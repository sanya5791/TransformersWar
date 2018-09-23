package com.akhutornoy.transformerswar.ui.transformerlist;

public class TransformerModel {
    private final String id;
    private final String name;
    private final String rate;
    private final String imageUrl;

    private TransformerModel(String id, String name, String rate, String imageUrl) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class Builder {
        private String id;
        private String name;
        private String rate;
        private String imageUrl;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRate(String rate) {
            this.rate = rate;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public TransformerModel build() {
            return new TransformerModel(id, name, rate, imageUrl);
        }
    }
}
