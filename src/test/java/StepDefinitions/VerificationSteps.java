package StepDefinitions;

import org.junit.Assert;

import apiEngine.EndPoints;
import apiEngine.IRestResponse;
import apiEngine.models.Book;
import apiEngine.models.requests.AddBooksRequest;
import apiEngine.models.requests.AuthorizationRequest;
import apiEngine.models.requests.ISBN;
import apiEngine.models.requests.RemoveBookRequest;
import apiEngine.models.responses.Books;
import apiEngine.models.responses.Token;
import apiEngine.models.responses.UserAccount;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class VerificationSteps extends BaseStep {

	public VerificationSteps(TestContext testContext) {
		super(testContext);
	}

	@Then("The book is added")
	public void bookIsAdded() {

		Book book = (Book) getScenarioContext().getContext(Context.BOOK);
		String userId = (String) getScenarioContext().getContext(Context.USER_ID);
		IRestResponse<UserAccount> userAccountResponse = (IRestResponse<UserAccount>) getScenarioContext()
				.getContext(Context.USER_ACCOUNT_RESPONSE);
		Assert.assertTrue(userAccountResponse.isSuccessful());
		Assert.assertEquals(201, userAccountResponse.getStatusCode());
		Assert.assertEquals(userId, userAccountResponse.getBody().userID);
		Assert.assertEquals(book.isbn, userAccountResponse.getBody().books.get(0).isbn);
	}

	@Then("The book is removed")
	public void bookIsRemoved() {
		String userId = (String) getScenarioContext().getContext(Context.USER_ID);
		Response response = (Response) getScenarioContext().getContext(Context.BOOK_REMOVE_RESPONSE);

		IRestResponse<UserAccount> userAccountResponse = (IRestResponse<UserAccount>) getScenarioContext()
				.getContext(Context.USER_ACCOUNT_RESPONSE);
		Assert.assertEquals(204, response.getStatusCode());

		userAccountResponse = getEndPoints().getUserAccount(userId);
		Assert.assertEquals(200, userAccountResponse.getStatusCode());
		Assert.assertEquals(0, userAccountResponse.getBody().books.size());

	}

}
