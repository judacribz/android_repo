package sheron.csci4100u.ass.a2.async_task;


public interface BitcoinPriceObserver {
    void bitcoinValueReceived(String bitcoinValue);
}
