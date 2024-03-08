package com.uni.mental.news;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {


    @GetMapping("newsList")
    public void news(Model model) throws IOException, FeedException {
        //RSS를 이용해서 뉴스를 실시간으로 받아서 업데이트 시켜준다.
        URL feedSource = new URL("https://www.psychiatricnews.net/rss/allArticle.xml");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        List<SyndEntry> entries = feed.getEntries();

        model.addAttribute("entries", entries);

        URL feedSource1 = new URL("https://api.newswire.co.kr/rss/industry/1005");
        SyndFeedInput input1 = new SyndFeedInput();
        SyndFeed feed1 = input1.build(new XmlReader(feedSource1));
        List<SyndEntry> entries1 = feed1.getEntries();

        model.addAttribute("entries1", entries1);

        URL feedSource2 = new URL("https://rss.blog.naver.com/ncmh0301.xml");
        SyndFeedInput input2 = new SyndFeedInput();
        SyndFeed feed2 = input2.build(new XmlReader(feedSource2));
        List<SyndEntry> entries2 = feed2.getEntries();
        List<SyndEntry> limitedEntries2 = entries2.stream().limit(30).toList();
        model.addAttribute("entries2", entries2);
    }

}
