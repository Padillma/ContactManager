package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class ContactManagerTest
{
    ContactManager contactManager;
     int beforeCount=0;


    @BeforeAll
    public void setupAll(){
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup(){
        contactManager = new ContactManager();
        beforeCount++;
    }

    @Test
   // @Disabled  // disables test so that it skips it entirely
    public void shouldCreateContact(){
        contactManager.addContact("John", "Doe", "0558887777");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0558887777"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should not Create Contact When First Name is NULL")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class,() ->{
            contactManager.addContact(null, "Doe", "123456789");
        });
    }
        @Test
        @DisplayName("Should not Create Contact When Last Name is NULL")
        public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                contactManager.addContact("John", null, "123456789");
            });
        }

    @Test
    @DisplayName("Should not Create Contact When Phone Number is NULL")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @AfterEach
    public void teardown(){
       // System.out.println("Should Execute After Each Test");
        System.out.println("current number of tests= "+ beforeCount);
    }

    @AfterAll
    public void tearDownAll() {
       // System.out.println("Should Execute after end of all Test");
        System.out.println("end number of tests= " + beforeCount);
    }

    @Test
    @DisplayName("Should Create Contact Only on MAC OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
    public void shouldCreateContactOnlyOnMAC(){
        contactManager.addContact("John", "Doe", "0558887777");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0558887777"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact on Windows OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled only on Windows OS")
    public void shouldNotCreateContactOnlyOnMAC(){
        contactManager.addContact("John", "Doe", "0558887777");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0558887777"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldTestContactCreationOnDEV(){  // edited run configurations, VM Options, added "-DENV=DEV". '-ea' was already there
        Assumptions.assumeTrue("TEST".equals(System.getProperty("ENV")));   // Change 'TEST' to 'DEV' to pass test
        contactManager.addContact("John", "Doe", "0558887777");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    @Nested
    class RepeatedNestedTest{
        @RepeatedTest(value= 5,
                name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        @DisplayName("Repeat Contact Creation Test 5 Times")
        public void shouldTestContactCreationRepeatedly(){
            contactManager.addContact("John", "Doe", "0558887777");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }
    }

    @Nested
    class ParameterizedNestedTest{
        @DisplayName("checking phonenumber valiation through parameterized test")
        @ParameterizedTest
        @ValueSource(strings = {"0234567890", "0987564123", "0547809123"})
        public void shouldTestContactCreationUsingValueSource(String phoneNumber){
            contactManager.addContact("John","Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }



        @DisplayName("CSV source - phone number should match required format")
        @ParameterizedTest
        @CsvSource({"0123456789","0231564897","0147258369"})
        public void shouldTestContactCreationUsingCSVSource(String phoneNumber){
            contactManager.addContact("John","Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }
    }
    @DisplayName("Method source - phone number should match required format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber){
        contactManager.addContact("John","Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList(){
        return Arrays.asList("0123456789","9876541230", "0852741963");
    }

}