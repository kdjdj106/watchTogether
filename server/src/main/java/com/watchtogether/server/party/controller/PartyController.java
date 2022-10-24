package com.watchtogether.server.party.controller;

import com.watchtogether.server.party.domain.model.AcceptPartyForm;
import com.watchtogether.server.party.domain.model.CreatePartyForm;
import com.watchtogether.server.party.domain.model.InvitePartyForm;
import com.watchtogether.server.party.service.partyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parties")
public class PartyController {
    private final partyService partyService;


    @PostMapping("/create")
    public ResponseEntity<?> createParty(@RequestBody CreatePartyForm form){
        return ResponseEntity.ok(partyService.createParty(form));
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptParty(@RequestBody AcceptPartyForm form){
        return ResponseEntity.ok(partyService.addMember(form));
    }



}