package com.lichysoft.recom;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

/**
 * Main Class starting app
 * To run this project add mahout to maven (you can import the library from repository: <a href="https://github.com/apache/mahout.git">...</a>)
 * The application represents Recommendation Engine 
 * Movies based on other users ratings 
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */

public class Recom {
    public static void main(String[] args) throws Exception {
        //building datamodel based on data.csv
    	RandomUtils.useTestSeed();       
        DataModel model = new FileDataModel(new File("C:\\Users\\Administrator\\Documents\\NetBeansProjects\\Recom\\src\\Data\\data.csv"));
        // Building Recommenderbuilder
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {            	
            	// looking for similaryty between users 
            	UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            	//looking for n closest users to a give user
            	UserNeighborhood neighborhood = new NearestNUserNeighborhood (100, similarity, model);            	
            	return new GenericUserBasedRecommender(model, neighborhood, similarity);            	
            }
        };
 
        // Recommend certain number of items for a particular user
        //!! Here, recommending 5 items to user_id = 2 (tu trzeba zmieni na naszych urzytkowników np Adam Lichy...
        Recommender recommender = recommenderBuilder.buildRecommender(model);
        String user = "Adam Lichy";
        int n = 5;
        List<RecommendedItem> recomendations = recommender.recommend(user, n);//tu nie wiem jak to zrobi zeby u nas userID to było np "Adam Lichy"
        for (RecommendedItem recommendedItem : recomendations) {
            System.out.println(recommendedItem);    
        }
        //tego w sumie nie potrzebujemy 
        // 0.7 = trainingPercentage percentage of each user’s preferences to use to produce recommendations, 1.0 = evaluationPercentage – percentage of users to use in evaluation
	//RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
	//double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
	//System.out.println("RMSE: " + score);
        
        //RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        //IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4, 0.7); // evaluate precision recall at 10
        
	//System.out.println("Precision: " + stats.getPrecision());
	//System.out.println("Recall: " + stats.getRecall());
	//System.out.println("F1 Score: " + stats.getF1Measure());               
    }
}