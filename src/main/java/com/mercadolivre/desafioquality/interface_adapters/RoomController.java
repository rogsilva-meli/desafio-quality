package com.mercadolivre.desafioquality.interface_adapters;

import com.mercadolivre.desafioquality.entity.dto.request.RoomRequestDTO;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.usecase.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class RoomController {

    private RoomService roomService;
    private PropertyService propertyService;

    public RoomController(RoomService roomService, PropertyService propertyService) {
        this.roomService = roomService;
        this.propertyService = propertyService;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAll(){
        List<Room> list = roomService.getAllRooms();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> create(@RequestBody RoomRequestDTO rDTO){
        Room room = roomService.saveRoom(rDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("/rooms/{nameProperty}/{nameRoom}")
    public ResponseEntity<Room> getRoom(@PathVariable String nameProperty,
                                        @PathVariable String nameRoom){
        Room r = roomService.getRoom(nameProperty, nameRoom);
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @PutMapping("/rooms/{nameProperty}/{nameRoom}")
    public ResponseEntity<RoomRequestDTO> modify(@PathVariable String nameProperty,
                                       @PathVariable String nameRoom,
                                       @RequestBody RoomRequestDTO r){
        RoomRequestDTO rDTO = roomService.modifyProperty(nameProperty, nameRoom, r);
        return ResponseEntity.status(HttpStatus.OK).body(rDTO);
    }

    @DeleteMapping("/rooms/{nameProperty}/{nameRoom}")
    @ResponseStatus(HttpStatus.OK)
    public void  delete(@PathVariable String nameProperty,
                        @PathVariable String nameRoom){
        roomService.deleteProperty(nameProperty, nameRoom);
    }


}
