
from pandas import read_csv
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
from sklearn.metrics import confusion_matrix
from sklearn.metrics import accuracy_score
from sklearn.svm import SVC

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
# Split-out validation dataset
array = dataset.values
X = array[:,0:4]
y = array[:,4]
X_train, X_validation, Y_train, Y_validation = train_test_split(X, y, test_size=0.20, random_state=1)
model = SVC(gamma='auto')
"""
Fitting data
"""
model.fit(X_train, Y_train)
"""
predicted data
"""
predictions = model.predict(X_validation)
"""
SVM Results
"""
print('Classification Reports:')
print(classification_report(Y_validation, predictions))
"""
Accuracy score
"""
print('Accuracy of SVM Algorithm: ')
print(accuracy_score(Y_validation, predictions))
"""
confusion matrix
"""
print('Confusion matrix ')
print(confusion_matrix(Y_validation, predictions))
