package com.diah24.splashactivity.api.model;

import com.google.gson.annotations.SerializedName;

public class DeletePostResponse {
        @SerializedName("status")
        private int status;
        @SerializedName("message")
        private String message;
        public DeletePostResponse() {
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
}
