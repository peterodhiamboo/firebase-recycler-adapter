package com.example.peter_odhiss.firebase_recycler_adapter;

public class User_data{
        private String firstName;
        private String lastName;

        public User_data() {
        }

        public User_data(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        
    }