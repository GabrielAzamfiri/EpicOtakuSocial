package com.example.EpicOtakuSocial.entities.anime;

@lombok.Data
public class Pagination {
    private long last_visible_page;
    private boolean has_next_page;
    private long current_page;
    private Items items;
}
