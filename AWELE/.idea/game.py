try:
    import numpy as np
except ImportError:
    print("Sorry, this example requires Numpy installed !")
    raise

from easyAI import TwoPlayerGame


class Awele(TwoPlayerGame):

    """
        Bazowa klasa do gry.
        Aby rozpocząć stwórz obiekt klasy Awele wkorzystując HumanPlayer() lub
        AI_Player().
        Zasady gry w Oware(Awele):
        https://bazgranie.znadplanszy.pl/2015/08/27/oware-i-rodzina-gier-mankala-od-starozytnosci-po-wspolczesnosc/
        z dodatkową zasadą zakonczenia gry gdzy na planszy zostanie mniej niz 7 ziaren.
    """

    def __init__(self, players):

        """
        Metoda do inicializacji gry.
            player.score -> wynik początkowy aktualnego gracza
            player.isstarved -> stan zagłodzenia aktualnego gracza
            player.row -> rząd ziaren przypisany do gracza ( 1: LKJIHG lub 2: ABCDEF)
            self.board -> inicjalizacja planszy. Każda przegródka  ma 4 ziarna
        """

        for i, player in enumerate(players):
            player.score = 0
            player.isstarved = False
            player.row = i
        self.players = players

        #A, B, C, D, E, F
        self.board = [4, 4, 4, 4, 4, 4,
                      4, 4, 4, 4, 4, 4]
        #L, K, J, I, H, G


        self.current_player = 1  # player 1 zaczyna.

    def make_move(self, move):

        """
        Metoda do wykonywania ruchu.

            #Brak możliwości ruchu
            Jeśli gracz nie ma ruchu ( move = Empty ) gra się kończy a gracz staje się zagłodzony (isstarved = true)
                Wówczas pozostałe ziarna z rzędu przeciwnika dopisywane są do konta gracza.

            #ROZSIEWANIE
            Jeśli możliwy jest ruch wszystkie ziarna są wyciągane z danej przegródki,
            następnie rozsiewa się te ziarna pojedynczo po każdej następnej przegródce.
            Nie można zasiać ziarna do przegródki z której się wyciągneło ziarno (zasiewana jest następna przegródka)

            #ZBIERANIE
            Jeśli ostatnie ziarno zostało posiane w przegródce przeciwnika
            i w tej przegródce znajdują się 2 lub 3 ziarna wówczas gracz zbiera wszystkie ziarna z tej przegródki.
            Czynność powtarza kolejno na poprzedniej przegródce przeciwnika jeśli są w niej 2 lub 3 ziarna.
            Jeśli nie ma odpowiedniej liczby zbieranie jest przerywane.

        Parameters
        -----------
        moves:
          Numer przegródki z której zostaną wyciągnięte ziarna
        """

        if move == "Empty": # Brak możliwości ruchu
            self.__starveThePlayer()
            return

        move = "abcdefghijkl".index(move)

        pos = move
        for i in range(self.board[move]):  # ROZSIEWANIE
            pos = (pos + 1) % 12
            if self.__isThisStartedHole(pos, move):
                pos = (pos + 1) % 12
            self.board[pos] += 1

        self.board[move] = 0

        while (pos / 6) == self.opponent.row and (self.board[pos] in [2, 3]):  # ZBIERANIE
            self.player.score += self.board[pos]
            self.board[pos] = 0
            pos = (pos - 1) % 12

    def possible_moves(self):

        """
        Metoda do zwracania możliwych ruchów do wykonania przez gracza.
        W pierwszej kolejności dozwolone są tylko te przegródki, po kótrych gracz będzie mógł
        zasiać przynajmniej jedną przegródkę przeciwnika.
        Jeśli nie ma takiej możliwości wówczas gracz może wciąźć dowolną, niepustą przegródkę
        Jeśli wszystkie są puste, następuje #ZAGŁODZENIE
        """

        if self.current_player == 1:
            if max(self.board[:6]) == 0:
                return ["Empty"] #ZAGŁODZENIE
            moves = [i for i in range(6) if (self.board[i] >= 6 - i)]
            if moves == []:
                moves = [i for i in range(6) if self.board[i] != 0]
        else:
            if max(self.board[6:]) == 0:
                return ["Empty"]
            moves = [i for i in range(6, 12) if (self.board[i] >= 12 - i)]
            if moves == []:
                moves = [i for i in range(6, 12) if self.board[i] != 0]

        return ["abcdefghijkl"[u] for u in moves]
    def show(self):
        """ Metoda do drukowania planszy """

        print("Score: %d / %d" % tuple(p.score for p in self.players))
        print("  ".join("lkjihg"))
        print(" ".join(["%02d" % i for i in self.board[-1:-7:-1]]))
        print(" ".join(["%02d" % i for i in self.board[:6]]))
        print("  ".join("abcdef"))

    def lose(self):

        """ Metoda decydująca o przegranej gdy przeciwnik zbierze wiecej niz 24 ziarna (ponad połowe wszystkich ziaren) """

        return self.opponent.score > 24

    def is_over(self):

        """ Metoda koncząca gre w przypadkach gdy jedna ze stron jest zagłodzona, jedna ze stron zbierze więcej niż połowe (24) ziaren lub na planszy zostanie mniej niz 7 ziaren (zasada wprowadzona na potrzeby skrócenia rozgrywki)"""

        return self.lose() or sum(self.board) < 7 or self.opponent.isstarved
    

    def __starveThePlayer(self):

        """
        Metoda do zagładzenia gracza.
        Obecny gracz jest zagładzany, a liczba ziaren z rzędu przeciwnika jest dodawana do konta przeciwnika.
        """

        self.player.isstarved = True
        index = 6 * self.opponent.row
        self.player.score += sum(self.board[index : index + 6])

    def __isThisStartedHole(self, actual, start):

        """
        Metoda sprawdza czy dana przegródka jest punktem startowym, z którego pobrano ziarna

         Parameters
        -----------

        actual:
            Aktualna przegródka

        start:
            Przegródka z której zostały wyciągnięte ziarna

        """

        return actual == start

if __name__ == "__main__":


    from easyAI import Human_Player, AI_Player, Negamax

    scoring = lambda game: game.player.score - game.opponent.score
    ai = Negamax(6, scoring)
    game = Awele([Human_Player(), AI_Player(ai)])
    game.play()
    if game.player.score > game.opponent.score:
        print("Player %d wins." % game.current_player)
    elif game.player.score < game.opponent.score:
        print("Player %d wins." % game.opponent_index)
    else:
        print("Looks like we have a draw.")
