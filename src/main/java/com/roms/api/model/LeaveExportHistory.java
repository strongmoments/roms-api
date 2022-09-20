package com.roms.api.model;

import com.roms.api.config.ModelHashMapConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Entity
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name ="leave_export_history")
public class LeaveExportHistory extends CommonFields implements Serializable {

    @Column(name="report_name")
    String reportName;

    @Column(name="dateRange")
    String dateRange;

    @Column(name="export_date")
    Instant exportDate;

    @OneToOne()
    @JoinColumn(name = "exportby_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe exportBy;


    @Type(type = "jsonb")
    @Column(name = "filters", columnDefinition = "json")
    //@Convert(converter = ModelHashMapConverter.class)
    private Map<String, String> filters;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public Instant getExportDate() {
        return exportDate;
    }

    public void setExportDate(Instant exportDate) {
        this.exportDate = exportDate;
    }

    public Employe getExportBy() {
        return exportBy;
    }

    public void setExportBy(Employe exportBy) {
        this.exportBy = exportBy;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }
}
