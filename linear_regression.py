import numpy 

my_data = numpy.genfromtxt('djia_temp.csv', delimiter=';', skip_header=1)
djia = my_data[:, 1]
highTemp = my_data[:, 2]
aveTemp = my_data[:, 3]
difTemp=numpy.absolute(numpy.subtract(highTemp,aveTemp))
weight = numpy.random.rand(1)
bias = numpy.random.rand(1)

def linearRegressionHigh ():
    learning_rate = 0.005
    global weight 
    global bias
    error = numpy.sum(highTemp*weight+bias - djia)
    init_cost = numpy.sum(numpy.power(error, 2))
    print 'high initial cost %s'% init_cost
    print 'high error is %s'%error
    i=0
    while (i<100):
        error = highTemp*weight+bias - djia
        #print 'updated error is %s'%error
        weight = weight - (learning_rate * error * highTemp / len(highTemp))
        bias = bias - (learning_rate * error * 1.0 / len(highTemp))
        i=i+1
    end_cost =numpy.sum(numpy.power(( highTemp*weight+bias - djia), 2))
    print 'high end cost %s'%end_cost

def linearRegressionAve ():
    learning_rate = 0.001
    global weight 
    global bias
    error = numpy.sum(aveTemp*weight+bias - djia)
    init_cost = numpy.sum(numpy.power(error, 2))
    print 'ave initial cost %s'% init_cost
    print 'ave error is %s'%error
    i=0
    while (i<100):
        error = aveTemp*weight+bias - djia
        #print 'updated error is %s'%error
        weight = weight - (learning_rate * error * aveTemp / len(aveTemp))
        bias = bias - (learning_rate * error * 1.0 / len(aveTemp))
        i=i+1
    end_cost =numpy.sum(numpy.power((aveTemp*weight+bias - djia), 2))
    print 'ave end cost %s'%end_cost

def linearRegressionDif ():
    learning_rate = 0.1
    global weight 
    global bias
    error = numpy.sum(difTemp*weight+bias - djia)
    init_cost = numpy.sum(numpy.power(error, 2))
    print 'dif initial cost %s'% init_cost
    print 'dif error is %s'%error
    i=0
    while (i<100):
        error = difTemp*weight+bias - djia
        #print 'updated error is %s'%error
        weight = weight - (learning_rate * error * difTemp / len(difTemp))
        bias = bias - (learning_rate * error * 1.0 / len(difTemp))
        i=i+1
    end_cost =numpy.sum(numpy.power((difTemp*weight+bias - djia), 2))
    print 'dif end cost %s'%end_cost

linearRegressionHigh()
linearRegressionAve()
linearRegressionDif()
