package com.stockpulse.api.market.service

import com.stockpulse.api.market.dto.MarketIndexResponse
import com.stockpulse.api.market.dto.NewsItemResponse
import org.springframework.stereotype.Service

@Service
class MarketService {

    fun getIndices(): List<MarketIndexResponse> = listOf(
        MarketIndexResponse(symbol = "SPX", name = "S&P 500", value = 5078.18, change = 41.63, changePercent = 0.83),
        MarketIndexResponse(symbol = "NDX", name = "NASDAQ 100", value = 17914.62, change = 183.02, changePercent = 1.03),
        MarketIndexResponse(symbol = "KOSPI", name = "KOSPI", value = 2642.36, change = -12.45, changePercent = -0.47),
        MarketIndexResponse(symbol = "DJI", name = "Dow Jones", value = 38654.42, change = 125.69, changePercent = 0.33),
    )

    fun getNews(): List<NewsItemResponse> = listOf(
        NewsItemResponse(id = "1", title = "NVIDIA 실적 발표, AI 반도체 수요 폭발적 성장", source = "Bloomberg", date = "2024-02-29", url = "#", symbol = "NVDA"),
        NewsItemResponse(id = "2", title = "Apple, 차세대 M4 칩 탑재 맥북 출시 예정", source = "Reuters", date = "2024-02-28", url = "#", symbol = "AAPL"),
        NewsItemResponse(id = "3", title = "Fed 금리 인하 신호에 기술주 강세", source = "CNBC", date = "2024-02-28", url = "#"),
        NewsItemResponse(id = "4", title = "삼성전자, HBM3E 양산 본격화", source = "한국경제", date = "2024-02-27", url = "#", symbol = "005930"),
        NewsItemResponse(id = "5", title = "Microsoft, OpenAI와 신규 파트너십 발표", source = "TechCrunch", date = "2024-02-27", url = "#", symbol = "MSFT"),
    )
}
