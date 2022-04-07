package books;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ca.Cal;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    Library library = new Library();
    List<Book> foundBooks = new ArrayList<>();


    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(String year, String month, String day){
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
    }
    @Given("a(nother) book with the title {string}, written by {string}, published in {int} March {int}")
    public void a_book_with_the_title_written_by_published_in_march(String name, String author, Integer int1, Integer int2) {
        LocalDateTime ldt = iso8601Date(int2.toString(),"03",int1.toString());
        Date published = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        Book book = new Book(name,author,published);
        library.addBook(book);
    }
    @Given("a(nother) book with the title {string}, written by {string}, published in {int} August {int}")
    public void a_book_with_the_title_written_by_published_in_august(String name, String author, Integer int1, Integer int2) {
        LocalDateTime ldt = iso8601Date(int2.toString(),"08",int1.toString());
        Date published = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        Book book = new Book(name,author,published);
        library.addBook(book);
    }
    @Given("a(nother) book with the title {string}, written by {string}, published in {int} January {int}")
    public void a_book_with_the_title_written_by_published_in_january(String name, String author, Integer int1, Integer int2) {
        LocalDateTime ldt = iso8601Date(int2.toString(),"01",int1.toString());
        Date published = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        Book book = new Book(name,author,published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {int} and {int}")
    public void the_customer_searches_for_books_published_between_and(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        Calendar cld = Calendar.getInstance();

        cld.set(Calendar.MONTH,Calendar.JANUARY);
        cld.set(Calendar.DAY_OF_MONTH,1);
        cld.set(Calendar.YEAR, int1);
        Calendar cld2 = Calendar.getInstance();
        cld2.set(Calendar.YEAR, int2);
        cld2.set(Calendar.MONTH,Calendar.DECEMBER);
        cld2.set(Calendar.DAY_OF_MONTH,30);
        Date y1 = cld.getTime();
        Date y2 = cld2.getTime();
        System.out.println(y1);
        foundBooks= library.findBooks(y1,y2);

    }
    @Then("{int} books should have been found")
    public void books_should_have_been_found(Integer int1) {
        for(Book b : foundBooks){System.out.println(b.getTitle());}
        System.out.println(int1+" "+ foundBooks.size());
        assertTrue(foundBooks.size()==int1);
    }
    @Then("Book {int} should have the title {string}")
    public void book_should_have_the_title(Integer int1, String string) {
            assertTrue(foundBooks.get(int1-1).getTitle().equals(string));
    }

}
