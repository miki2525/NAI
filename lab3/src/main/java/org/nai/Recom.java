package org.nai;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
import org.nai.db.DataLoader;
import org.nai.db.IDsHolder;

import java.io.*;
import java.util.List;

/**
 * Main Class starting app
 * To run this project add mahout to maven (you can import the library from repository: <a href="https://github.com/apache/mahout.git">...</a>)
 * The application represents Recommendation Engine
 * Movies based on other users ratings
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class Recom {
    public static void main(String[] args) throws IOException, TasteException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("data.csv");
        File csvFile = getCSVFile(inputStream);
        //building datamodel based on data.csv
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(csvFile);
        // Building Recommenderbuilder
        RecommenderBuilder recommenderBuilder = (DataModel model1) -> {
            // looking for similaryty between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model1);
            //looking for n closest users to a give user
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model1);
            return new GenericUserBasedRecommender(model1, neighborhood, similarity);
        };

        // Recommend certain number of items for a particular user        
        Recommender recommender = recommenderBuilder.buildRecommender(model);

        String user = "Marcin Żmuda-Trzebiatowski";
        int n = 5;
        long id = IDsHolder.getIdForUser(user);
        List<RecommendedItem> recomendations = recommender.recommend(id, n);
        System.out.println("============================");
        System.out.println("Rekomendacje dla usera: " + user);
        System.out.println("Ilość rekomendacji: " + n);
        System.out.println("============================");
        for (RecommendedItem recommendedItem : recomendations) {
            String movie = IDsHolder.getMovieById(recommendedItem.getItemID()).toUpperCase();
            float rate = recommendedItem.getValue();
            System.out.println("Film: " + movie + ", Ocena [ " + rate + " ]");
            System.out.println();
        }
    }

    /**
     * get csv file with data
     * @param inputStream
     * @return File
     * @throws IOException
     */
    private static File getCSVFile(InputStream inputStream) throws IOException {
        File file;
        if (inputStream != null) {
            file = File.createTempFile("dataRecom", ".csv");
            file.deleteOnExit();
            FileOutputStream out = new FileOutputStream(file);
            IOUtils.copy(inputStream, out);
            DataLoader.loadData(); //load IDsHolder
        } else {
            file = DataLoader.initData(); //create csv + load IDsHolder
        }
        return file;
    }
}
