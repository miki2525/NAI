import cv2
import numpy as np
import mediapipe as mp
import tensorflow as tf
from keras.models import load_model
import pyautogui as keyboard

"""
Hand Gesture Handler
The app use hand gestures to control the media player
Authors: Adam Lichy, Mikołaj Kalata
To run the program you need to install tensorflow, pyautogui and mediapipe, numpy and cv2 packages.
Program can control the video media player by hand gesture captured in the camera
There are 4 options of media player which you can control by gestures: 
1. Pause / Play
2. Volume +
3. Volume -
4. Forward
"""

"""
initialize and setup mediapipe
"""
mpHands = mp.solutions.hands
hands = mpHands.Hands(max_num_hands=1, min_detection_confidence=0.7)
mpDraw = mp.solutions.drawing_utils

"""
load the gesture recognizer model (TensorFlow pre-trained model)
"""
model = load_model('mp_hand_gesture_data')

"""
load gesture(control options) names
"""
f = open('gesture.names', 'r')
classNames = f.read().split('\n')
f.close()
print(classNames)

"""
initialize the webcam and capture the gesture
"""
cap = cv2.VideoCapture(0)

while True:
    _, frame = cap.read()

    x, y, c = frame.shape

    """
    flip the frame vertically
    """
    frame = cv2.flip(frame, 1)
    framergb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    """
    get hand landmark prediction
    """
    result = hands.process(framergb)

    className = ''
    classID = None

    if result.multi_hand_landmarks:
        landmarks = []
        for handslms in result.multi_hand_landmarks:
            for lm in handslms.landmark:
                lmx = int(lm.x * x)
                lmy = int(lm.y * y)

                landmarks.append([lmx, lmy])
            """
            drawing landmarks on frames
            """
            mpDraw.draw_landmarks(frame, handslms, mpHands.HAND_CONNECTIONS)

            """
            predict gesture
            """
            prediction = model.predict([landmarks])

            """
            get index and name of the prediction(gesture)
            """
            classID = np.argmax(prediction)
            className = classNames[classID]

    """
    show the prediction in the frame
    """
    cv2.putText(frame, className, (10, 50), cv2.FONT_HERSHEY_SIMPLEX,
                1, (0, 0, 255), 2, cv2.LINE_AA)
    """
    perform action in media player
    """
    if classID == 2:
        keyboard.press("up")
    elif classID == 3:
        keyboard.press("down")
    elif classID == 5:
        keyboard.press("space")
    elif classID == 6:
        keyboard.press("right")
    else:
        pass

    """
    show the Gestures Handler Dialog
    """
    cv2.imshow("Gesture Handler", frame)

    """
    quite the app if 'q' is hit
    """

    if cv2.waitKey(1) == ord('q'):
        break

"""
release the webcam and destroy all active windows
"""
cap.release()
cv2.destroyAllWindows()