//package com.geekbrains.spring.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class CartTest {
//    @Autowired
//    private CartService cartService;
//
//    @MockBean
//    private BookService bookService;
//
//    @BeforeEach
//    public void initCart() {
//        cartService.clearCart("test_cart");
//    }
//
//    @Test
//    public void addToCartTest() {
//        Book book = new Book();
//        book.setId(5L);
//        book.setTitle("X");
//        book.setPrice(BigDecimal.valueOf(100.0));
//
//        Genre genre = new Genre();
//        genre.setTitle("X");
//        Author author = new Author();
//        author.setName("X");
//        book.setGenre(genre);
//        book.setAuthor(author);
//
//        Mockito.doReturn(Optional.of(book)).when(bookService).findById(5L);
//        cartService.addToCart("test_cart", 5L);
//        cartService.addToCart("test_cart", 5L);
//        cartService.addToCart("test_cart", 5L);
//
//        Mockito.verify(bookService, Mockito.times(1)).findById(ArgumentMatchers.eq(5L));
//        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
//    }
//}