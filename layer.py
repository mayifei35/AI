#import matplotlib.pyplot as plt
import numpy as np
np.random.seed(42)

def sigmoid(x):
    return 1 / (1 + np.exp(-x))

def dsigmoid(x):
    return x * (1 - x)

class layer:
    def __init__(self, in_size, out_size, learning_rate):
        self.in_size = in_size
        self.out_size = out_size
        self.weights = np.random.rand(in_size,out_size)
        self.learning_rate = learning_rate
    
    #first layer(10,25),second layer(25,1)
    def forward(self, X):
        self.incoming = X
        print'x',X.shape,'weights',self.weights.shape
        act = X.dot(self.weights)
        act = sigmoid(act)
        self.outputs = act
        return act

    def backward(self, err):
        err = err * dsigmoid(self.outputs)
        update = self.incoming.T.dot(err)
        weight_c= self.learning_rate*(update.T.dot(self.weights)).T.dot(err.T)
        self.weights=self.weights.dot(weight_c)
        return update.T

    def reportAccuracy(self,X, y):
        out = self.forward(X)
        out = np.round(out)
        count = np.count_nonzero(y - out.T)
        correct = len(X) - count
        print "%.4f" % (float(correct)*100.0 / len(X))
       
    def calculateDerivError(self, y, pred):
        return 2*(y - pred)
        
    def calculateError(self, y, pred):
        print 'y',y.shape
        print 'pred',pred.shape
        return (np.sum(np.power((y - pred), 2)))

            
def loadDataset(filename='wine.data'):
    #my_data = np.genfromtxt(filename, delimiter=',', skip_header=1)
    my_data = np.genfromtxt(filename, delimiter=',')
    # The labels of the cases
    # Raw labels are either 4 (cancer) or 2 (no cancer)
    # Normalize these classes to 0/1
    #y = (my_data[:, 10] / 2) - 1
    y = my_data[:, 0 ]
    # Case features
    #X = my_data[:, :10]
    X = my_data[:1, :14]
    # Normalize the features to (0, 1)
    X_norm = X / X.max(axis=0)
    return X_norm, y
        
def gradientChecker(layer, X, y):
    epsilon = 1E-5
    layer.weights[1] += epsilon
    out1 = layer.forward(X)
    err1 = layer.calculateError(y, out1)
    layer.weights[1] -= 2*epsilon
    out2 = layer.forward(X)
    err2 = layer.calculateError(y, out2)
    numeric = (err2 - err1) / (2*epsilon)
    print 'numeric is ',numeric
    layer.weights[1] += epsilon
    out3 = layer.forward(X)
    err3 = layer.calculateDerivError(y, out3)
    derivs = layer.backward(err3)
    print 'deravative is ',derivs[1]
    
if __name__=="__main__":
    X, y = loadDataset()
    layer_one = layer(13,25,0.0632)
    layer_two = layer(25,1,0.1)
    number_epochs=100
    #gradientChecker(layer_one, X, y)
    #baked in process
    for i in range(number_epochs):
        activation=layer_one.forward(X)
        out=layer_two.forward(activation)
        print'forward twice'
        err = layer_two.calculateError(y, out)
        deriv_err = layer_two.calculateDerivError(y, out)
        back=layer_two.backward(deriv_err)
        back_back=layer_one.backward(back)
        print'backward twice '
        layer_one.reportAccuracy(X, y)
    
    
    
