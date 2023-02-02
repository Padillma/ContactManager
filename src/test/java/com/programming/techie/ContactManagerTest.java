package com.programming.techie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest
{
    @Test
    public void shouldCreateContact(){
        ContactManager contactManager = new ContactManager();
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
        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class,() ->{
            contactManager.addContact(null, "Doe", "123456789");
        });
    }
        @Test
        @DisplayName("Should not Create Contact When Last Name is NULL")
        public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
            ContactManager contactManager = new ContactManager();
            Assertions.assertThrows(RuntimeException.class, () -> {
                contactManager.addContact("John", null, "123456789");
            });
        }

    @Test
    @DisplayName("Should not Create Contact When Phone Number is NULL")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }
}