package Assignment;

import java.util.*;

public class LibrarySystem {
    static Scanner sc = new Scanner(System.in);
    public static void addBooks(List<Book> books) {
        System.out.print("Enter book name : ");
        String name = sc.nextLine();
        System.out.print("Enter author name : ");
        String author = sc.nextLine();
        System.out.print("Enter ISBN : ");
        String isbn = sc.nextLine();
        books.add(new Book(name,author,isbn));
    }
    public static void display(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public static void removeBooks(List<Book> books) {
        System.out.print("Enter Book name : ");
        String delete = sc.nextLine();
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book book = it.next();
            if (book.getTitle().equals(delete)) {
                it.remove();
            }
        }
    }

    public static void searchBooks(List<Book> books) {
        System.out.print("Enter book name : ");
        String search = sc.nextLine();
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book book = it.next();
            if (book.getTitle().contains(search)) {
                System.out.println(book.getTitle() + book.getAuthor());
            }
        }
    }
    public static void main(String[] args) {
        List<Book> books = new ArrayList<Book>();

        boolean a = true;
        while (a == true) {
            System.out.println("Press 1 to add new books : \nPress 2 to remove : \nPress 3 to search a book :\nPress 4 to display books : ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    addBooks(books)
                    ;
                    System.out.println("Book added : " + books + "\n");
                    break;
                case "2":
                    removeBooks(books)
                    ;
                    System.out.println("Book removed : " + books + "\n");
                    break;
                case "3":
                    searchBooks(books)
                    ;
                    System.out.println("Result : " + books);
                    break;
                case "4":
                    display(books)
                    ;
                default:
                    a = false;
                    break;
            }
        }


    }
}
