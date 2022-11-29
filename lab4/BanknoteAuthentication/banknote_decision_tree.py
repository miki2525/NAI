import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn import tree
from sklearn.metrics import accuracy_score, classification_report

"""
Banknote Authentication Decision Tree
Authors: Adam Lichy, Mikołaj Kalata
To run program you need to install numpy, matplotlib, and sklearn packages.
Program shows decision tree algorithm used for banknote authentication dataset
(https://machinelearningmastery.com/standard-machine-learning-datasets/)
There are 5 attributes: 
1. variance of Wavelet Transformed image (continuous)
2. skewness of Wavelet Transformed image (continuous)
3. curtosis of Wavelet Transformed image (continuous)
4. entropy of image (continuous)
5. class (integer)
"""

"""
Load input data
"""
banknote = pd.read_csv("data_banknote_authentication.csv")
X,y = banknote.drop('class',axis=1), banknote['class']
class_0 = np.array(X[y == 0])
class_1 = np.array(X[y == 1])
"""
Visualization
"""

plt.scatter(class_0[:, 0], class_0[:, 1], s=75, facecolors='red',
            edgecolors='black', linewidth=1, marker='H')
plt.scatter(class_1[:, 0], class_1[:, 1], s=75, facecolors='blue',
            edgecolors='black', linewidth=1, marker='*')
plt.title('banknote authentication prediction')
plt.show()
"""
Splitting and fitting data
"""
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=1)
dt = tree.DecisionTreeClassifier()
dt.fit(X_train, y_train)
"""
Prediction & Report
"""
y_pred = dt.predict(X_test)
print("PREDICTION: ", accuracy_score(y_pred, y_test))
print("REPORT: ", classification_report(y_test,y_pred))
