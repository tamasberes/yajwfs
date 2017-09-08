package hu.yajwfs.model;

public class SecondaryGenreResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "SecondaryGenreResponse{" +
                "response=" + response +
                '}';
    }

    public class Response {
        private String statusCode;

        private Data data;

        private String statusText;

        public String getStatusCode() {
            return statusCode;
        }

        public Data getData() {
            return data;
        }

        public String getStatusText() {
            return statusText;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "statusCode='" + statusCode + '\'' +
                    ", data=" + data +
                    ", statusText='" + statusText + '\'' +
                    '}';
        }

        public class Data {
            private Genrelist genrelist;

            @Override
            public String toString() {
                return "Data{" +
                        "genrelist=" + genrelist +
                        '}';
            }

            public Genrelist getGenrelist() {
                return genrelist;
            }

            public class Genrelist {
                private Genre[] genre;

                public Genre[] getGenre() {
                    return genre;
                }

            }
        }
    }


}
