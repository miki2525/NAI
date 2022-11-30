import numpy
import warnings
import pandas
from matplotlib import pyplot
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn import tree


"""
Iris decision tree
Authors: Adam Lichy, Mikołaj Kalata
To run program you need to install numpy, matplotlib, and sklearn packages.
Program shows decision tree algorithm used for Iris Data Set
(https://archive.ics.uci.edu/ml/datasets/Iris)
There are 5 attributes: 
1. sepal length in cm
2. sepal width in cm
3. petal length in cm
4. petal width in cm
5. class:
-- Iris Setosa
-- Iris Versicolour
-- Iris Virginica
"""
warnings.filterwarnings("ignore")
"""
Load Data
"""
input_file = 'Iris.csv'
dataset = numpy.loadtxt(input_file, delimiter=',')
X, y = dataset[:, :4], dataset[:, -1]

class_0 = numpy.array(X[y == 0])
class_1 = numpy.array(X[y == 1])
class_2 = numpy.array(X[y == 2])
class_3 = numpy.array(X[y == 3])
"""
Visualization
"""

pyplot.scatter(class_0[:, 0], class_0[:, 1], s=75, facecolors='black',
            edgecolors='black', linewidth=1, marker='x')
pyplot.scatter(class_1[:, 0], class_1[:, 1], s=75, facecolors='pink',
            edgecolors='black', linewidth=1, marker='o')
pyplot.scatter(class_2[:, 0], class_2[:, 1], s=75, facecolors='green',
            edgecolors='black', linewidth=1, marker='p')
pyplot.scatter(class_3[:, 0], class_3[:, 1], s=75, facecolors='red',
            edgecolors='black', linewidth=1, marker='X')
pyplot.title('Iris prediction')
pyplot.show()

"""
Splitting and fitting data
"""
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=1)
dt = tree.DecisionTreeClassifier()
dt.fit(X_train, y_train)
"""
Prediction & Report
"""
expected_y = y_test
predicted_y = dt.predict(X_test)

class_names = ['Iris-setosa', 'Iris-versicolor', 'Iris-virginica']
print(metrics.classification_report(expected_y, predicted_y, target_names=class_names))