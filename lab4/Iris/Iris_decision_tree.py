import numpy
from pandas import read_csv
from matplotlib import pyplot
from sklearn.model_selection import train_test_split
from sklearn import tree
from sklearn.metrics import accuracy_score, classification_report

"""
Iris SVM
Authors: Adam Lichy, Mikołaj Kalata
To run program you need to install numpy, matplotlib, and sklearn packages.
Program shows SVM algorithm used for Iris Data Set
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

"""
Load Data
"""
url = "https://raw.githubusercontent.com/jbrownlee/Datasets/master/iris.csv"
names = ['sepal-length', 'sepal-width', 'petal-length', 'petal-width', 'class']
dataset = read_csv(url, names=names)

X,y = dataset.drop('class',axis=1), dataset['class']
class_0 = numpy.array(X[y == 0])
class_1 = numpy.array(X[y == 1])
"""
Visualization
"""

pyplot.scatter(class_0[:, 0], class_0[:, 1], s=75, facecolors='red',
            edgecolors='black', linewidth=1, marker='H')
pyplot.scatter(class_1[:, 0], class_1[:, 1], s=75, facecolors='blue',
            edgecolors='black', linewidth=1, marker='*')
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
y_pred = dt.predict(X_test)
print("PREDICTION: ", accuracy_score(y_pred, y_test))
print("REPORT: ", classification_report(y_test,y_pred))