package hu.yajwfs.model;

import com.google.gson.annotations.SerializedName;

public class PrimaryGenreResponse {

    private Response response;

    public PrimaryGenreResponse() {
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "PrimaryGenreResponse{" +
                "response=" + response +
                '}';
    }

    public class Response {

        private int statusCode;
        private String statusText;

        private Data data;

        public Response() {
        }

        public Data getData() {
            return data;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusText() {
            return statusText;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "statusCode=" + statusCode +
                    ", statusText='" + statusText + '\'' +
                    ", data=" + data +
                    '}';
        }

        public class Data {
            @SerializedName("genrelist")
            private GenreList genreList;

            public Data() {
            }

            public GenreList getGenreList() {
                return genreList;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "genreList=" + genreList +
                        '}';
            }
        }
    }
}
