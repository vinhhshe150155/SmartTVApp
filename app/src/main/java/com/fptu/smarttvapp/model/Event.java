package com.fptu.smarttvapp.model;

public class Event {
    private String imageUrl;
    private String videoUrl;
    private String qrCode;

    public Event() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Event(String imageUrl, String videoUrl, String qrCode) {
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.qrCode = qrCode;
    }
}
