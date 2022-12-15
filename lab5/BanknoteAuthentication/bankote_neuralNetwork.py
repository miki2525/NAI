import pandas as pd
import tensorflow as tf
from sklearn.metrics import classification_report,confusion_matrix
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

"""
Banknote Authentication Neural Network
Authors: Adam Lichy, Mikołaj Kalata
To run program you need to install tensorflow, pandas and sklearn packages.
Program shows neural network (tensorflow) used for banknote authentication dataset
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

"""
Data scaling and converting to data frames.
"""

Scaler = StandardScaler()
Scaler.fit(banknote.drop('class',axis =1))
Scaled_features = Scaler.fit_transform(banknote.drop('class',axis= 1))
df_features = pd.DataFrame(Scaled_features,columns = banknote.columns[:-1])
x = df_features
y = banknote['class']

"""
Splitting data frames into training and testing datasets.
"""

x_train , x_test , y_train , y_test = train_test_split(x, y, test_size=0.3)

"""
Prepare model
"""

variance = tf.feature_column.numeric_column("variance")
skewness = tf.feature_column.numeric_column('skewness')
curtosis = tf.feature_column.numeric_column('curtosis')
entropy = tf.feature_column.numeric_column('entropy')

feat_cols = [variance,skewness,curtosis,entropy]
classifier = tf.estimator.DNNClassifier(hidden_units=[10,20,10],n_classes = 2,feature_columns = feat_cols)

input_func = tf.compat.v1.estimator.inputs.pandas_input_fn(x=x_train,y=y_train,batch_size=20,shuffle=True)
classifier.train(input_fn=input_func,steps = 500)

"""
Model evaluation
"""

pred_func = tf.compat.v1.estimator.inputs.pandas_input_fn(x=x_test,batch_size=len(x_test),shuffle= False)
note_predictions = list(classifier.predict(input_fn=pred_func))
final_preds  = []
for pred in note_predictions:
    final_preds.append(pred['class_ids'][0])

print(confusion_matrix(y_test,final_preds))
print(classification_report(y_test,final_preds))
