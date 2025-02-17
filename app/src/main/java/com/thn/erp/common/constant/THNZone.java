package com.thn.erp.common.constant;

import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.common.Zone;
import com.qiniu.android.common.ZoneInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephen on 2018/3/29 18:38
 * Email: 895745843@qq.com
 */

public class THNZone extends Zone {
    /**
     * 华东机房
     */
    public static final Zone zone0 = new FixedZone(new String[]{
            "up.qbox.me"
    });

    /**
     * 华北机房
     */
    public static final Zone zone1 = new FixedZone(new String[]{
            "upload-z1.qiniup.com", "up-z1.qiniup.com",
            "upload-z1.qbox.me", "up-z1.qbox.me"
    });

    /**
     * 华南机房
     */
    public static final Zone zone2 = new FixedZone(new String[]{
            "upload-z2.qiniup.com", "upload-dg.qiniup.com",
            "upload-fs.qiniup.com", "up-z2.qiniup.com",
            "up-dg.qiniup.com", "up-fs.qiniup.com",
            "upload-z2.qbox.me", "up-z2.qbox.me"
    });

    /**
     * 北美机房
     */
    public static final Zone zoneNa0 = new FixedZone(new String[]{
            "upload-na0.qiniup.com", "up-na0.qiniup.com",
            "upload-na0.qbox.me", "up-na0.qbox.me"
    });

    /**
     * 新加坡机房
     */
    public static final Zone zoneAs0 = new FixedZone(new String[]{
            "upload-as0.qiniup.com","up-as0.qiniup.com",
            "upload-as0.qbox.me","up-as0.qbox.me"
    });

    private ZoneInfo zoneInfo;

    public THNZone(ZoneInfo zoneInfo) {
        this.zoneInfo = zoneInfo;
    }

    public THNZone(String[] upDomains) {
        this.zoneInfo = createZoneInfo(upDomains);
    }

    public static ZoneInfo createZoneInfo(String[] upDomains) {
        List<String> upDomainsList = new ArrayList<String>();
        Map<String, Long> upDomainsMap = new ConcurrentHashMap<String, Long>();
        for (String domain : upDomains) {
            upDomainsList.add(domain);
            upDomainsMap.put(domain, 0L);
        }
        return new ZoneInfo(0, upDomainsList, upDomainsMap);
    }

    @Override
    public synchronized String upHost(String upToken, boolean useHttps, String frozenDomain) {
        String upHost = this.upHost(this.zoneInfo, useHttps, frozenDomain);
        for (Map.Entry<String, Long> entry : this.zoneInfo.upDomainsMap.entrySet()) {
            Log.d("Qiniu.FixedZone", entry.getKey() + ", " + entry.getValue());
        }
        return upHost;
    }

    @Override
    public void preQuery(String token, QueryHandler complete) {
        complete.onSuccess();
    }

    @Override
    public boolean preQuery(String token) {
        return true;
    }

    @Override
    public synchronized void frozenDomain(String upHostUrl) {
        if (upHostUrl != null) {
            URI uri = URI.create(upHostUrl);
            //frozen domain
            String frozenDomain = uri.getHost();
            zoneInfo.frozenDomain(frozenDomain);
        }
    }
}
