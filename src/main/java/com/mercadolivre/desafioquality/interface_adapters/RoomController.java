package com.mercadolivre.desafioquality.interface_adapters;

import com.mercadolivre.desafioquality.entity.dto.request.RoomRequestDTO;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.usecase.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<RoomResponseDTO>> getAll(){
        List<RoomResponseDTO> list = roomService.getAllRooms();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomResponseDTO> create(@Valid @RequestBody RoomRequestDTO rDTO){
        RoomResponseDTO room = roomService.saveRoom(rDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("/rooms/{nameProperty}/{nameRoom}")
    public ResponseEntity<RoomResponseDTO> getRoom(@PathVariable String nameProperty,
                                        @PathVariable String nameRoom){
        RoomResponseDTO r = roomService.getRoom(nameProperty, nameRoom);
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @PutMapping("/rooms/{nameProperty}/{nameRoom}")
    public ResponseEntity<RoomResponseDTO> modify(@PathVariable String nameProperty,
                                       @PathVariable String nameRoom,
                                       @Valid @RequestBody RoomRequestDTO r){
        RoomResponseDTO rDTO = roomService.modify(nameProperty, nameRoom, r);
        return ResponseEntity.status(HttpStatus.OK).body(rDTO);
    }

    @DeleteMapping("/rooms/{nameProperty}/{nameRoom}")
    @ResponseStatus(HttpStatus.OK)
    public void  delete(@PathVariable String nameProperty,
                        @PathVariable String nameRoom){
        roomService.delete(nameProperty, nameRoom);
    }


}
