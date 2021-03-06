/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.model;

import java.time.LocalDate;

/**
 *
 * @author joshuansu
 */
public class CD {

    private long id = -1;
    private String musicGenreCode;
    private String outletCode;
    private long CDSetid = -1;
    private String title;
    private String cost;
    private LocalDate dateReleased;
    private int numberOfTracks;
    private int totalPlayingTimes;
    private boolean variousArtists;
    private String recordCompanyCode;
    private long idArtist = -1;

    public CD() {
    }

    public long getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(long idArtist) {
        this.idArtist = idArtist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMusicGenreCode() {
        return musicGenreCode;
    }

    public void setMusicGenreCode(String musicGenreCode) {
        this.musicGenreCode = musicGenreCode;
    }

    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    public long getCDSetid() {
        return CDSetid;
    }

    public void setCDSetid(long CDSetid) {
        this.CDSetid = CDSetid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public LocalDate getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(LocalDate dateReleased) {
        this.dateReleased = dateReleased;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public int getTotalPlayingTimes() {
        return totalPlayingTimes;
    }

    public void setTotalPlayingTimes(int totalPlayingTimes) {
        this.totalPlayingTimes = totalPlayingTimes;
    }

    public boolean isVariousArtists() {
        return variousArtists;
    }

    public void setVariousArtists(boolean variousArtists) {
        this.variousArtists = variousArtists;
    }

    public String getRecordCompanyCode() {
        return recordCompanyCode;
    }

    public void setRecordCompanyCode(String recordCompanyCode) {
        this.recordCompanyCode = recordCompanyCode;
    }
}
