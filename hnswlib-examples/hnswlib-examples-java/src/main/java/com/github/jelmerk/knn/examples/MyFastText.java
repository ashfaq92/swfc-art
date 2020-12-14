package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.Index;
import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.ArrayList;
import java.util.List;



public class MyFastText {

    public static void main(String[] args) throws Exception {
        ArrayList<Word> words = new ArrayList<Word>();

        float[] arrDog = {-0.4f, 0.37f, 0.02f, -0.34f};
        Word w1 = new Word("dog", arrDog);
        words.add(0,w1);

        float[] arrCat = {-0.15f, -0.02f, -0.23f, -0.23f};
        Word w2 = new Word("cat", arrCat);
        words.add(0,w2);

        float[] arrLion = {0.19f, -0.4f, 0.35f, -0.48f};
        Word w3 = new Word("lion", arrLion);
        words.add(0,w3);

        float[] arrTiger = {-0.08f, 0.31f, 0.56f, 0.07f};
        Word w4 = new Word("tiger", arrTiger);
        words.add(0,w4);

        float[] arrCheetah = {0.27f, -0.28f, -0.2f, -0.43f};
        Word w5 = new Word("cheetah", arrCheetah);
        words.add(0,w5);

        float[] arrRat = {0.21f, -0.48f, -0.56f, -0.37f};
        Word w6 = new Word("rat", arrRat);
        words.add(0,w6);

        System.out.println("Constructing index.");

        HnswIndex<String, float[], Word, Float> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.FLOAT_INNER_PRODUCT, words.size())
                .withM(16)
                .withEf(200)
                .withEfConstruction(200)
                .build();
        hnswIndex.addAll(words, (workDone, max) -> System.out.printf("Added %d out of %d words to the index.%n", workDone, max));

        System.out.printf("Creating index with %d words\n", hnswIndex.size());
        Index<String, float[], Word, Float> groundTruthIndex = hnswIndex.asExactIndex();
        int k = 10;
        String input = "cat";
        List<SearchResult<Word, Float>> approximateResults = hnswIndex.findNeighbors(input, k);
        List<SearchResult<Word, Float>> groundTruthResults = groundTruthIndex.findNeighbors(input, k);
        System.out.println("\nMost similar words found using HNSW index:");
        for (SearchResult<Word, Float> result : approximateResults) {
            System.out.printf("%s %.4f%n", result.item().id(), result.distance());
        }
        System.out.println("\nMost similar words found using exact index: ");
        for (SearchResult<Word, Float> result : groundTruthResults) {
            System.out.printf("%s %.4f%n", result.item().id(), result.distance());
        }
        int correct = groundTruthResults.stream().mapToInt(r -> approximateResults.contains(r) ? 1 : 0).sum();
       System.out.printf("%nAccuracy : %.4f%n%n", correct / (double) groundTruthResults.size());
    }
}
