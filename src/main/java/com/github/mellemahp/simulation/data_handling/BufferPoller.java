package com.github.mellemahp.simulation.data_handling;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;
import com.github.mellemahp.simulation.poisoning.PoisonPill;
import com.github.mellemahp.simulation.poisoning.PoisonVial;
import com.github.mellemahp.sqlite_data_processing.SQLiteExecutable;
import com.github.mellemahp.sqlite_data_processing.SQLiteJDBCConnector;
import com.github.mellemahp.sqlite_data_processing.SQLiteWriter;

import lombok.CustomLog;

@CustomLog
public class BufferPoller implements Callable<Integer> {
    private final BlockingQueue<SQLiteSerializable> dataBus;
    private final SQLiteJDBCConnector connector;
    private final SubPoller subpoller;
    private final SQLiteWriter writer;

    public BufferPoller(BlockingQueue<SQLiteSerializable> dataBus,
            SQLiteJDBCConnector connector,
            int vialCapacity,
            SQLiteWriter sqliteWriter) {
        this.dataBus = dataBus;
        this.connector = connector;
        this.writer = sqliteWriter;
        this.subpoller = new SubPoller(vialCapacity);
    }

    @Override
    public Integer call() {
        log.info("Polling for data");
        connector.executeInConnectionContext(subpoller);
        log.info("Closing poller.");

        return 0;
    }

    private class SubPoller implements SQLiteExecutable {
        private final PoisonVial poisonVial;

        public SubPoller(int vialCapacity) {
            this.poisonVial = new PoisonVial(vialCapacity);
        }

        @Override
        public void execute(Connection connection) throws SQLException {
            pollTillPoisoned(connection);
        }

        private void pollTillPoisoned(Connection connection) throws SQLException {
            while (this.poisonVial.isNotFull()) {
                pollForData(connection);
            }
    
            log.info("Flushing buffer...");
            writer.flushBuffer(connection);
            log.info("Done flushing.");
        }

        private void pollForData(Connection connection) throws SQLException {
            SQLiteSerializable result = dataBus.poll();
            if (result == null) { 
                return;
            }
    
            if (result instanceof PoisonPill) { 
                log.info("Got Poison pill from Simulation");
                this.poisonVial.addPoisonPill(result);
            } else {
                writer.add(result, connection);
            }
        }
    

    }
    
}