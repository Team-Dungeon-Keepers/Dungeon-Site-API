package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.GameAddress;
import com.revature.dungeonsite.repositories.GameAddressRepository;
//import com.revature.dungeonsite.utils.PasswordUtils;
import com.revature.dungeonsite.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//possibly change mapping later?
@RestController
@CrossOrigin
@RequestMapping("/api/gameaddress")
public class GameAddressController {
    private GameAddressRepository gar;
	
    public GameAddressController(GameAddressRepository gar) {
        this.gar = gar;
    }

	private GameAddress getNeoGameAddress(@PathVariable("id") Long ID) throws ResourceNotFoundException {
        return gar.findByAddressID(ID);
    }

    @GetMapping
    public ResponseEntity<List<GameAddress>> findAll() {
        return ResponseEntity.ok(this.gar.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameAddress> findByID(@PathVariable(value="id") Long ID)
            throws ResourceNotFoundException {
        GameAddress gameAddress = getNeoGameAddress(ID);
        return ResponseEntity.ok().body(gameAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameAddress> updateGameAddress(@PathVariable(value = "id") Long ID,
        @RequestBody GameAddress gameAddress) throws ResourceNotFoundException {
        GameAddress neoGameAddress = getNeoGameAddress(ID);
        if (gameAddress.getGameID() != null)
            neoGameAddress.setGameID(gameAddress.getGameID());
        if (gameAddress.getAddressID() != null)
            neoGameAddress.setAddressID(gameAddress.getAddressID());
        return ResponseEntity.ok(this.gar.save(neoGameAddress));
    }

    @PostMapping
    public GameAddress makeGameAddress(@RequestBody GameAddress neoGameAddress) {
		neoGameAddress.setID(KeyUtils.nextKey());
        return this.gar.save(neoGameAddress);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteGameAddress(@PathVariable(value = "id") Long ID)
            throws ResourceNotFoundException {
        GameAddress oldGameAddress = getNeoGameAddress(ID);
        this.gar.delete(oldGameAddress);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
