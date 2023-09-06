package com.khteam2.connectgym.customer_service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> viewToAll() {
        List<Notice> noticeList = noticeRepository.findAllByOrderByNoticeDatetimeDesc();
        return noticeList;
    }

    public List<Notice> viewTopList(List<Notice> noticeList) {

        List<Notice> noticeTopList = new ArrayList<>();
        for (int i = 0; i < noticeList.size(); i++) {
            if (noticeList.get(i).getTopContent() == 1) {
                noticeTopList.add(noticeList.get(i));
            }
        }
        return noticeTopList;
    }

    public List<Notice> viewOtherList(List<Notice> noticeList) {

        List<Notice> noticeOtherList = new ArrayList<>();
        for (int i = 0; i < noticeList.size(); i++) {
            if (noticeList.get(i).getTopContent() == 0) {
                noticeOtherList.add(noticeList.get(i));
            }
        }
        return noticeOtherList;
    }

    public int getTotalPages(int itemsPerPage, List<Notice> list) {
        int totalItems = list.size();
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

    public List<Notice> getDataForPage(int pageNumber, int itemsPerPage,
        List<Notice> list) {
        List<Notice> dataForPage = new ArrayList<>();

        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, list.size());

        for (int i = startIndex; i < endIndex; i++) {
            dataForPage.add(list.get(i));
        }
        return dataForPage;
    }

}
