import entity.Base;
import entity.Owner;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.text.StringEscapeUtils;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.*;

public class StackOverflowTest {

    private int statusCode = 200;

    @BeforeMethod
    public void setup() {
        baseURI = "https://api.stackexchange.com/2.2";
        port = 443;
    }

    @AfterMethod
    public void afterMethod() {
        reset();
    }

    @Parameters({"site", "page", "pagesize", "order", "sort", "filter"})
    @Test
    public void answersApiTest(String site, String page, int pagesize, String order, String sort, String filter) throws UnsupportedEncodingException {

        ValidatableResponse validatableResponse = queryParameters(site, page, pagesize, order, sort, filter).get("/answers")
                .then().assertThat().statusCode(statusCode);

        var items = validatableResponse.extract().as(Base.class).getItems();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(items.size() <= pagesize);

        for (int i = 0; i < items.size(); i++) {
            Owner owner = items.get(i).getOwner();
            softAssert.assertNotNull(owner);
            softAssert.assertTrue(owner.getLink().contains(owner.getUserId().toString()) &&
                    owner.getLink().contains(stringConverter(owner.getDisplayName())), owner.toString());
        }
        softAssert.assertAll();
    }

    public RequestSpecification queryParameters(String site, String page, int pagesize, String order, String sort, String filter) {
        return given().queryParam("site", site)
                .queryParam("page", page).queryParam("pagesize", pagesize)
                .queryParam("order", order)
                .queryParam("sort", sort)
                .queryParam("filter", filter);
    }

    public String stringConverter(String string) throws UnsupportedEncodingException {
        String normalString = StringEscapeUtils.unescapeHtml4(string).replaceAll("\\s*-\\s*|\\s+|_", "-")
                .replaceAll("-\\.-|-\\.|\\.-", "-").replaceAll("\\.", "-");
        return URLEncoder.encode(normalString, StandardCharsets.UTF_8.displayName()).toLowerCase();
    }
}
