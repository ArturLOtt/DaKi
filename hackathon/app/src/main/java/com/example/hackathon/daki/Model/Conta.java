package com.example.hackathon.daki.Model;

/**
 * Created by Vaharamus on 22/03/2018.
 */

public class Conta {


        private String username;
        private String password;

        public Conta(String username, String senha) {
            this.username = username;
            this.password = senha;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

