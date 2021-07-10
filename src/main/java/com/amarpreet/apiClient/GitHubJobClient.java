package com.amarpreet.apiClient;

import com.amarpreet.domain.gitHub.GitHubPosition;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GitHubJobClient {

    private WebClient webClient;

    public GitHubJobClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGitHubJobAPI_withPageNumber(int pageNum,
                                                                  String description){
        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNum)
                .buildAndExpand()
                .toString();
        System.out.println("uri ::" + uri);
        List<GitHubPosition> gitHubPositions = webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();
        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGitHubJobAPI_withMultiplePageNumber(List<Integer> pageNumbers,
                                                                  String description){
        long startTime = System.currentTimeMillis();
        List<GitHubPosition> gitHubPositions = pageNumbers
                .stream()
                .map(pageNumber -> invokeGitHubJobAPI_withPageNumber(pageNumber,description))
                .flatMap(Collection::stream
                ).collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken:::" + (endTime - startTime));
        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGitHubJobAPI_withMultiplePageNumber_approch2(List<Integer> pageNumbers,
                                                                          String description){
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers
                .stream()
                .map(pageNumber ->
                        CompletableFuture
                                .supplyAsync(() ->
                                        invokeGitHubJobAPI_withPageNumber(
                                                pageNumber,
                                                description)))
                .collect(Collectors.toList());


        List<GitHubPosition> gitHubPositionList = gitHubPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken:::" + (endTime - startTime));

        return gitHubPositionList;
    }


    public List<GitHubPosition> invokeGitHubJobAPI_withMultiplePageNumber_approch3(List<Integer> pageNumbers,
                                                                                   String description){
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers
                .stream()
                .map(pageNumber ->
                        CompletableFuture
                                .supplyAsync(() ->
                                        invokeGitHubJobAPI_withPageNumber(
                                                pageNumber,
                                                description)))
                .collect(Collectors.toList());

       CompletableFuture<Void> cfAll =  CompletableFuture
                .allOf(gitHubPositions
                        .toArray(new CompletableFuture[gitHubPositions.size()]));

        List<GitHubPosition> gitHubPositionList = cfAll
                .thenApply(v -> gitHubPositions.stream()
                             .map(CompletableFuture::join)
                             .flatMap(Collection::stream)
                             .collect(Collectors.toList()))
                .join();


        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken:::" + (endTime - startTime));

        return gitHubPositionList;
    }
}
