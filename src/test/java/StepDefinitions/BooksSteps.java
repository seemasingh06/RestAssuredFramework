package StepDefinitions;

import org.junit.Assert;

import apiEngine.IRestResponse;
import apiEngine.models.Book;
import apiEngine.models.requests.AddBooksRequest;
import apiEngine.models.requests.ISBN;
import apiEngine.models.requests.RemoveBookRequest;
import apiEngine.models.responses.Books;
import apiEngine.models.responses.UserAccount;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class BooksSteps extends BaseStep{

	public BooksSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	@Given("A list of books are available")
	public void listOfBooksAreAvailable() {
		IRestResponse<Books> booksResponse = getEndPoints().getBooks();
    	Book book = booksResponse.getBody().books.get(0);
    	getScenarioContext().setContext(Context.BOOK, book);
	}

	@When("I add a book to my reading list")
	public void addBookInList() {
		Book book = (Book)getScenarioContext().getContext(Context.BOOK);
		String userId = (String) getScenarioContext().getContext(Context.USER_ID);
		ISBN isbn = new ISBN(book.isbn);
		AddBooksRequest addBooksRequest = new AddBooksRequest(userId, isbn);
		IRestResponse<UserAccount> userAccountResponse = getEndPoints().addBook(addBooksRequest);
		getScenarioContext().setContext(Context.USER_ACCOUNT_RESPONSE, userAccountResponse);
	}



	@When("I remove a book from my reading list")
	public void removeBookFromList() {
		Book book = (Book) getScenarioContext().getContext(Context.BOOK);
		String userId = (String) getScenarioContext().getContext(Context.USER_ID);
		RemoveBookRequest removeBookRequest = new RemoveBookRequest(userId, book.isbn);
		Response response = getEndPoints().removeBook(removeBookRequest);
		getScenarioContext().setContext(Context.BOOK_REMOVE_RESPONSE, response);
	}


}
