package qdu.graduation.backend.services;

import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.NewsDao;
import qdu.graduation.backend.entity.News;
import qdu.graduation.backend.utils.OkHttpUtil;
import qdu.graduation.backend.utils.RegexUtil;

import java.io.IOException;
import java.util.Date;

@Service
public class CrawlerNews {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewsDao newsDao;

    @Scheduled(cron = "0 6 14 * * ?")
    public void crawler() {
        logger.info("开始爬取");
        String indexUrl = "http://jw.qdu.edu.cn/homepage/index.do";
        Response response = OkHttpUtil.get(indexUrl, null);
        try {
            indexParser(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexParser(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select(".mainContent td>div>a");
        for (Element element : elements) {
            String detailUrl = "http://jw.qdu.edu.cn" + element.attr("href");
            requestDetail(detailUrl);
            System.out.println(detailUrl);
        }
    }

    public void requestDetail(String url) {
        Response response = OkHttpUtil.get(url, null);
        try {
            datailParser(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void datailParser(String page) {
        Document document = Jsoup.parse(page);
        String title = document.select("#article>h2").text();
        String content = document.select("#article .body").text();
        String publishTime = RegexUtil.findOne(document.select("#articleInfo li:contains(发布日期)").text(), "\\d.+");
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setPublishTime(publishTime);
        news.setCrawlTime(new Date());
        logger.info("爬取:" + title);
        newsDao.insert(news);

    }

    public static void main(String[] args) {
        new CrawlerNews().crawler();
    }
}
