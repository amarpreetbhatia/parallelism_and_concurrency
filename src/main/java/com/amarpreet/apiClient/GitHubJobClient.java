package com.amarpreet.apiClient;

import com.amarpreet.domain.gitHub.GitHubPosition;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
}
