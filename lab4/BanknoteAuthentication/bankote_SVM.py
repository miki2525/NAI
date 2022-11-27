import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn import metrics

"""
Banknote Authentication SVM
Authors: Adam Lichy, Mikołaj Kalata
To run program you need to install numpy, matplotlib, and sklearn packages.
Program shows SVM algorithm used for banknote authentication dataset
(https://machinelearningmastery.com/standard-machine-learning-datasets/)
There are 5 attributes: 
1. variance of Wavelet Transformed image (continuous)
2. skewness of Wavelet Transformed image (continuous)
3. curtosis of Wavelet Transformed image (continuous)
4. entropy of image (continuous)
5. class (integer)
"""

"""
Load Data
"""
banknote = pd.read_csv("data_banknote_authentication.csv")
print(banknote.head(5))
x,y = banknote.drop('class',axis=1), banknote['class']
x_train, x_test, y_train, y_test = train_test_split(x,y, test_size=0.20)
svm = SVC(kernel='rbf', C=100.0)
"""
Fitting data
"""
svm.fit(x_train, y_train)
"""
predicted data
"""
y_predict = svm.predict(x_test)
"""
SVM Results
"""
print('Classification Reports:')
print(metrics.classification_report(y_test, y_predict))
"""
Accuracy score
"""
print('Accuracy of SVM Algorithm: '
      , metrics.accuracy_score(y_test, y_predict)*100)

"""
F1 Score
"""
f1_score_SVM = metrics.f1_score(y_test, y_predict, average='micro')
"""
confusion matrix
"""
cm_SVM = metrics.confusion_matrix(y_test, y_predict)
cm_log = metrics.confusion_matrix(y_test, y_predict)
"""
recall
"""
recall_SVM = metrics.recall_score(y_test, y_predict)
"""
Heatmap confusion matrix
"""
sns.heatmap(cm_log, annot=True, fmt=".0f", linewidths=3, square=True, cmap='Blues', color="#cd1076")
plt.ylabel('actual label')
plt.xlabel('predicted label')
"""
show F1 Score and Recall
"""
plt.title(f'F1 Score [SVM Algorithm]: {f1_score_SVM:.2f}\n'
          f'Recall [SVM Algorithm]: {recall_SVM:.2f}', size=14, color='black')
plt.show()
