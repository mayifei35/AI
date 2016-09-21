import numpy as np

#a point is one movie and the cluster it belongs to
class Point:
    def __init__(self,_cluster):
        self.cluster = _cluster


###suppose this returns
def getData(filename='u.csv'):
    my_data = np.genfromtxt(filename, delimiter=',')
    rawData=my_data[:,:3]
    rawData=rawData.astype(int)
    movieList=[]

    ###make every row a dictionary
    for i in range(len(rawData)):
        userID=rawData[i,0]
        movieID=rawData[i,1]
        rating=rawData[i,2]
        movie={'movieID':movieID,'userID':userID,'rating':rating}
        movieList.append(movie)
    print 'before',len(movieList)
    #print movieList[8]
    listLen=len(movieList)
    m=0
    ###merge when movieID=movieID
    for m in range(listLen-1):
        for k in range(m+1,listLen):
            curr= movieList[m]
            nextM=movieList[k]
            if k==1000 or k==5000:
                print curr,nextM
            if curr['movieID']==nextM['movieID']:
               curr.update(nextM)
               movieList.remove(movieList[k])###need to be fixed
                   
    print 'after',len(movieList)
getData()

""" 

###make a random dictionary i.e. generate a rating        
def makeRandomPoint(n):
    for i in range(n):
        userID=random.uniform(0,1000)
        rating =random.uniform(0, 5)
        newMovie={'userID':userID,'rating':rating}
    return newMovie
 
###between centroids. how do I PASS IN all centroids? is this right?? 
def distanceBetween():
   return a+b

def main():
    # How many points are in our dataset?
    num_points = 1682
    # For each of those points how many dimensions do they have?
    dimensions = 2
    # Bounds for the values of those points in each dimension
    lower = 1
    upper = 5
    # The K in k-means. How many clusters do we assume exist?
    num_clusters = 30
    # When do we say the optimization has 'converged' and stop updating clusters
    opt_cutoff = 0.5
    # Generate some points need a loop here
    makeRandomPoint(dimensions, lower, upper) 
    # Cluster those data!
    cluster rs = kmeans(points, num_clusters, opt_cutoff)


    # Print our clusters
    for i,c in enumerate(clusters):
        for p in c.points:
            print " Cluster: ", i, "\t Point :", p
 
class Cluster:
#A set of points and their centroid

    def __init__(self, pointSets):
        if len(pointSets) == 0: raise Exception("ILLEGAL: empty cluster")
        # The points that belong to this cluster
        self.pointSets = pointSets
        # The dimensionality of the points in this cluster
        self.n = pointSets[0].n
        # Assert that all points are of the same dimensionality
        for p in pointSets:
            if p.n != self.n: raise Exception("ILLEGAL: wrong dimensions")
        # Set up the initial centroid (this is usually based off one point)
        self.centroid = self.calculateCentroid()
     
    ### Returns the distance between the previous centroid and the new after recalculating and storing the new centroid.
    def update(self, pointSets):
        old_centroid = self.centroid
        self.pointSets = pointSets
        self.centroid = self.calculateCentroid()
        shift = getDistance(old_centroid, self.centroid)
        return shift

    ###Finds a virtual center point for a group of n-dimensional points
    def calculateCentroid(self):
        numPoints = len(self.pointSets)
        # Get ratings in this cluster
        total=0
        for n in pointSets:
            total=total+pointSets[n][rating]
        # Calculate the mean for each dimension
        centroid_rating = total/n
        return centroid_rating
    
    
    def distanceWithin:


    


def kmeans(points, k, cutoff):
    # Pick out k random points to use as our initial centroids
    initial = random.sample(points, k)
    # Create k clusters using those centroids
    clusters = [Cluster([p]) for p in initial]
    # Loop through the dataset until the clusters stabilize
    loopCounter = 0
    while True:
        # Create a list of lists to hold the points in each cluster
        lists = [ [] for c in clusters]
    clusterCount = len(clusters)
    # Start counting loops
    loopCounter += 1
    # For every point in the dataset ...
    for p in points:
        # Get the distance between that point and the centroid of the first
        # cluster.
        smallest_distance = getDistance(p, clusters[0].centroid)
        # Set the cluster this point belongs to
        clusterIndex = 0
        
        # For the remainder of the clusters ...
        for i in range(clusterCount - 1):
            # calculate the distance of that point to each other cluster's
            # centroid.
            distance = getDistance(p, clusters[i+1].centroid)
            # If it's closer to that cluster's centroid update what we
            # think the smallest distance is, and set the point to belong
            # to that cluster
            if distance < smallest_distance:
                smallest_distance = distance
                clusterIndex = i+1
                lists[clusterIndex].append(p)
                # Set our biggest_shift to zero for this iteration
                biggest_shift = 0.0
                # As many times as there are clusters ...
                for i in range(clusterCount):
                # Calculate how far the centroid moved in this iteration
                    shift = clusters[i].update(lists[i])
                    # Keep track of the largest move from all cluster centroid updates
                    biggest_shift = max(biggest_shift, shift)
                # If the centroids have stopped moving much, say we're done!
                if biggest_shift < cutoff:
                    print "Converged after %s iterations" % loopCounter
                    break
            return clusters

main()


    Loop until centroids dont change much:
        For each input feature vector, v:
            Calculate the distance from v to each of the K centroids.
            Assign v to its closest centroid. 
        Recalculate centroids by averaging features of all their member vectors

"""






