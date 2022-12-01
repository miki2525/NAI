import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import itertools as it
from sklearn.model_selection import train_test_split
from sklearn.svm import SVR
from sklearn import metrics
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
"""
Load Data
"""
df=pd.read_csv('concrete_data.csv', sep=',') # Create a dataframe
df.head(5)  

"""
Visualization
"""
fig = plt.figure(figsize=(13,8))
ax = fig.add_subplot(111)
plt.scatter(df["water"],df["cement"],
            c=df["concrete_compressive_strength"],s=df["concrete_compressive_strength"]*3,
            linewidth=1,edgecolor="k",cmap="viridis")
ax.set_xlabel("water")
ax.set_ylabel("cement")
lab = plt.colorbar()
lab.set_label("concrete_compressive_strength")
plt.title("cement vs water")
plt.show()

"""
Splitting and fitting data
"""

train,test = train_test_split(df,test_size =.3,random_state = 0)
train_X = train[[x for x in train.columns if x not in ["concrete_compressive_strength"] + ["age_months"]]]
train_Y = train["concrete_compressive_strength"]
test_X  = test[[x for x in test.columns if x not in ["concrete_compressive_strength"] + ["age_months"]]]
test_Y  = test["concrete_compressive_strength"]
"""
Prediction & Report
"""
svm= SVR(kernel='linear')
model4=svm.fit(train_X, train_Y)
predictions4 = svm.predict(test_X)
m4=model4.score(test_X, test_Y)
RMSE4=np.sqrt(metrics.mean_squared_error(test_Y, predictions4))
print('Accuracy of model is', model4.score(test_X, test_Y))
print('Mean Absolute Error:', metrics.mean_absolute_error(test_Y, predictions4))  
print('Mean Squared Error:', metrics.mean_squared_error(test_Y, predictions4))  
print('Root Mean Squared Error:', np.sqrt(metrics.mean_squared_error(test_Y, predictions4)))

