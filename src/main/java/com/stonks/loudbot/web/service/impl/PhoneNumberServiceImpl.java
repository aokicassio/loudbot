package com.stonks.loudbot.web.service.impl;

import com.stonks.loudbot.web.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Value("${number.cas}")
    private String phoneNumberCassio;

    @Value("${number.bru}")
    private String phoneNumberBruno;

    @Value("${number.gre}")
    private String phoneNumberGregor;

    @Value("${number.phi}")
    private String phoneNumberPhilippe;

    @Override
    public List<String> getPhoneNumbers() {
        //Could be replaced by list in db
        return Arrays.asList(
            //phoneNumberBruno,
            phoneNumberCassio
            //phoneNumberGregor,
            //phoneNumberPhilippe
        );
    }
}
