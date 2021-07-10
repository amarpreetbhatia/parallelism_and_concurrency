package com.amarpreet.apiClient;

import com.amarpreet.domain.gitHub.GitHubPosition;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubJobClientTest {

    WebClient webClient = WebClient.create("https://jobs.github.com/");
    GitHubJobClient gitHubJobClient = new GitHubJobClient(webClient);

    @Test
    void invokeGitHubJobAPI_withPageNumber() {
        int pageNum = 1;
        String description = "javascript";
        List<GitHubPosition> gitHubPositions = gitHubJobClient
                .invokeGitHubJobAPI_withPageNumber(pageNum, description);
        System.out.println("gitHubPositions" + gitHubPositions);
        assertTrue(gitHubPositions.isEmpty());

    }

    @Test
    void invokeGitHubJobAPI_withMultiplePageNumber(){
        List<Integer> pageNumbers = List.of(1,2,3);
        String description = "javascript";
        List<GitHubPosition> gitHubPositions = gitHubJobClient
                .invokeGitHubJobAPI_withMultiplePageNumber(pageNumbers, description);
        System.out.println("gitHubPositions" + gitHubPositions);
        assertTrue(gitHubPositions.isEmpty());
    }
}