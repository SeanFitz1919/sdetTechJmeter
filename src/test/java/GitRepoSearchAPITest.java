import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
public class GitRepoSearchAPITest {

    public static int globalThreadCount = 1;
    public static int globalIterationCount = 1;
    public static String globalHost = "https://api.github.com/search/repositories?";


    @Test
    public void getRepoSearchRepoCountWithJava() throws IOException {
        String host = globalHost;

        System.out.println("Starting Testing of url: ");

        String queryParameter = "q=";
        String keywordSearch1 = "manga";
        String proLangStrut = "+language:";
        String urlFetchGitReposByPLang = host+queryParameter+keywordSearch1+proLangStrut+"Java";
        int repoTotalCountPass = 1272;
        boolean jsonValueCheck = false;

        System.out.println("Testing URl urlFetchGitReposByPLang:"+urlFetchGitReposByPLang);

        TestPlanStats tPlanGetRepoSearchRepoCountWithJava = testPlan(
                threadGroup(globalThreadCount, globalIterationCount,
                        httpSampler(urlFetchGitReposByPLang)
                                .children(
                                       jsonAssertion("total_count").equalsTo(repoTotalCountPass)
                                )
                )//, resultsTreeVisualizer() //Note: For additional debugging test plan assertions
        ).run();
        /*
        * Note: For running at scale see -- https://abstracta.github.io/jmeter-java-dsl/guide/#run-test-at-scale
        * By using runIn() four options are available for running the jmeter tests at scale.
        *  - BlazeMeter -
        *   runIn(new BlazeMeterEngine(System.getenv("BZ_TOKEN"))
        *  - OctoPerf -
        *   runIn(new OctoPerfEngine(System.getenv("OCTOPERF_API_KEY"))
        *  - Azure Load Testing -
        *   runIn(new AzureEngine(System.getenv("AZURE_CREDS"))
        *  - JMeter remote testing -
        *   runIn(new DistributedJmeterEngine("host1", "host2"));
         * */

        assertThat(tPlanGetRepoSearchRepoCountWithJava.overall().errorsCount()).isZero();
        assertThat(tPlanGetRepoSearchRepoCountWithJava.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

    @Test
    public void getRepoSearchRepoCountWithJavaFailingTest() throws IOException {
        String host = globalHost;

        System.out.println("Starting Testing of url: ");

        String queryParameter = "q=";
        String keywordSearch1 = "manga";
        String proLangStrut = "+language:";
        String urlFetchGitReposByPLang = host+queryParameter+keywordSearch1+proLangStrut+"Java";
        int repoTotalCountPass = 99999;

        System.out.println("Testing URl urlFetchGitReposByPLang:"+urlFetchGitReposByPLang);

        TestPlanStats urlFetchGitRepoBYPLang = testPlan(
                threadGroup(globalThreadCount, globalIterationCount,
                        httpSampler(urlFetchGitReposByPLang)
                                .children(
                                        jsonAssertion("total_count").equalsTo(repoTotalCountPass)
                                )
                )
        ).run();
        assertThat(urlFetchGitRepoBYPLang.overall().errorsCount()).isZero();
        assertThat(urlFetchGitRepoBYPLang.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

    @Test
    public void getRepoSearchRepoCountWithPythonFilterByCreation() throws IOException {

        String url = globalHost+"q=playstation+created:%3C2015-06-17+language:Python&sort=stars&order=desc";
        int repoTotalCountPass = 18;

        TestPlanStats urlFetchGitRepoBYPLang = testPlan(
                threadGroup(globalThreadCount, globalIterationCount,
                        httpSampler(url)
                                .children(
                                        jsonAssertion("total_count").equalsTo(repoTotalCountPass),
                                        jsonAssertion("items[0].name").equalsTo("memcarduino")
                                )
                )//, resultsTreeVisualizer() //Note: For additional debugging test plan assertions
        ).run();

        assertThat(urlFetchGitRepoBYPLang.overall().errorsCount()).isZero();
        assertThat(urlFetchGitRepoBYPLang.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

    @Test
    public void getRepoCountWithPythonAndPerlSortedByCreation() throws IOException {

        String url = globalHost+"q=playstation+created:%3C2015-06-17+language:Python+language:Perl&sort=stars&order=asc";

        TestPlanStats urlFetchGitRepoBYPLang = testPlan(
                threadGroup(globalThreadCount, globalIterationCount,
                        httpSampler(url)
                                .children(
                                        jsonAssertion("total_count").equalsTo(19)
                                )
                )//, resultsTreeVisualizer() //Note: For additional debugging test plan assertions
        ).run();

        assertThat(urlFetchGitRepoBYPLang.overall().errorsCount()).isZero();
        assertThat(urlFetchGitRepoBYPLang.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

    @Test
    public void getReposForUser() throws IOException {

        String url = globalHost+"q=gameboy+user:Humpheh";

        TestPlanStats urlFetchGitRepoBYPLang = testPlan(
                threadGroup(globalThreadCount, globalIterationCount,
                        httpSampler(url)
                                .children(
                                        jsonAssertion("total_count").equalsTo(1),
                                        jsonAssertion("items[0].name").equalsTo("goboy")
                                )
                )//, resultsTreeVisualizer() //Note: For additional debugging test plan assertions
        ).run();

        assertThat(urlFetchGitRepoBYPLang.overall().errorsCount()).isZero();
        assertThat(urlFetchGitRepoBYPLang.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

    @Test
    public void getReposWithMostStarsLimitedNumberResultsAndSortDecreasing() throws IOException {

        int numberOfResults = 15;
        String url = globalHost+"q=raspberrypi&s=stars&o=desc&per_page="+numberOfResults+"&page=1";

        TestPlanStats urlFetchGitRepoBYPLang = testPlan(
                threadGroup(globalThreadCount, globalIterationCount,
                        httpSampler(url)
                                .children(
                                        jsonAssertion("length(items)").equalsTo(numberOfResults),
                                        jsonAssertion("items[0].name").equalsTo("raspberrypi")
                                )
                )//, resultsTreeVisualizer() //Note: For additional debugging test plan assertions
        ).run();
        assertThat(urlFetchGitRepoBYPLang.overall().errorsCount()).isZero();
        assertThat(urlFetchGitRepoBYPLang.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
    }

}
