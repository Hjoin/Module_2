package com.techelevator.auctions.controller;

import com.techelevator.auctions.DAO.AuctionDAO;
import com.techelevator.auctions.DAO.MemoryAuctionDAO;
import com.techelevator.auctions.model.Auction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionDAO dao;

    public AuctionController() {
        this.dao = new MemoryAuctionDAO();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Auction> list(@RequestParam (required = false) String title_like, @RequestParam (required = false) Double currentBid_lte) {
        if (title_like != null && currentBid_lte != null) {
            return dao.searchByTitleAndPrice(title_like, currentBid_lte);
        }
        if(title_like == null) {
            title_like = "";
        }
        if(currentBid_lte == null) {
            currentBid_lte = 0.00;
        }
        if(currentBid_lte > 0) {
            return dao.searchByPrice(currentBid_lte);
        }
        return dao.searchByTitle(title_like);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Auction get(@PathVariable int id) {
        return dao.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Auction createAuction(@RequestBody Auction auction){
        return dao.create(auction);
    }

}
