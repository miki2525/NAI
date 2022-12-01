import numpy
import warnings
from matplotlib import pyplot as plt
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn import tree
from sklearn import preprocessing
from sklearn import utils


"""
Concrete decision tree
Authors: Adam Lichy, Mikołaj Kalata
To run program you need to install numpy, matplotlib, and sklearn packages.
Program shows decision tree algorithm used for Concrete Data Set
(https://archive.ics.uci.edu/ml/datasets/Concrete+Compressive+Strength)
Number of instances (observations): 1030
Number of Attributes: 9
Attribute breakdown: 8 quantitative input variables, and 1 quantitative output variable

Name -- Data Type -- Measurement -- Description

Cement (component 1) -- quantitative -- kg in a m3 mixture -- Input Variable
Blast Furnace Slag (component 2) -- quantitative -- kg in a m3 mixture -- Input Variable
Fly Ash (component 3) -- quantitative -- kg in a m3 mixture -- Input Variable
Water (component 4) -- quantitative -- kg in a m3 mixture -- Input Variable
Superplasticizer (component 5) -- quantitative -- kg in a m3 mixture -- Input Variable
Coarse Aggregate (component 6) -- quantitative -- kg in a m3 mixture -- Input Variable
Fine Aggregate (component 7) -- quantitative -- kg in a m3 mixture -- Input Variable
Age -- quantitative -- Day (1~365) -- Input Variable
Concrete compressive strength -- quantitative -- MPa -- Output Variable 
"""
warnings.filterwarnings("ignore")
"""
Load Data
"""
input_file = 'concrete_data.csv'
dataset = numpy.loadtxt(input_file, delimiter=',')
X, y = dataset[:, :8], dataset[:, -1]

class_0 = numpy.array(X[y == 0])
class_1 = numpy.array(X[y == 1])
class_2 = numpy.array(X[y == 2])
class_3 = numpy.array(X[y == 3])
class_4 = numpy.array(X[y == 4])
class_5 = numpy.array(X[y == 5])
class_6 = numpy.array(X[y == 6])
class_7 = numpy.array(X[y == 7])
"""
Visualization
"""

plt.scatter(class_0[:, 0], class_0[:, 1], s=75, facecolors='black',
            edgecolors='black', linewidth=1, marker='x')
plt.scatter(class_1[:, 0], class_1[:, 1], s=75, facecolors='white',
            edgecolors='black', linewidth=1, marker='o')
plt.scatter(class_2[:, 0], class_2[:, 1], s=75, facecolors='green',
            edgecolors='black', linewidth=1, marker='p')
plt.scatter(class_3[:, 0], class_3[:, 1], s=75, facecolors='red',
            edgecolors='black', linewidth=1, marker='X')
plt.scatter(class_4[:, 0], class_4[:, 1], s=75, facecolors='blue',
            edgecolors='black', linewidth=1, marker='+')
plt.scatter(class_5[:, 0], class_5[:, 1], s=75, facecolors='orange',
            edgecolors='black', linewidth=1, marker='*')
plt.scatter(class_6[:, 0], class_6[:, 1], s=75, facecolors='purple',
            edgecolors='black', linewidth=1, marker='x')
plt.scatter(class_7[:, 0], class_7[:, 1], s=75, facecolors='brown',
            edgecolors='black', linewidth=1, marker='P')

plt.title('Concrete compressive strength prediction')
plt.show()

"""
Splitting and fitting data
"""
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.10, random_state=5)
dt = tree.DecisionTreeClassifier()
dt.fit(X_train, y_train)
"""
Prediction & Report
"""
expected_y = y_test
predicted_y = dt.predict(X_test)

print(metrics.classification_report(expected_y, predicted_y))