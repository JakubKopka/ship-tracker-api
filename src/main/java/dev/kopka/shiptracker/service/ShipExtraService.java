package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.domain.model.ShipExtra;
import dev.kopka.shiptracker.domain.model.ShipType;
import dev.kopka.shiptracker.repository.ShipExtraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipExtraService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final ShipExtraRepository shipExtraRepository;

    public ShipExtraService(ShipExtraRepository shipExtraRepository) {
        this.shipExtraRepository = shipExtraRepository;
    }

    // https://wiki.barentswatch.net/pages/viewpage.action?pageId=3670811
    public ShipExtra getShipExtraByNumber(int number) {
        ShipType shipType;
        if (number >= 20 && number <= 29) {
            shipType = ShipType.WING_IN_GROUND;
        } else if (number == 30) {
            shipType = ShipType.FISHING;
        } else if (number == 33) {
            shipType = ShipType.DREDGING_OR_UNDERWATER_OPS;
        } else if (number == 34) {
            shipType = ShipType.DIVING_OPS;
        } else if (number == 35) {
            shipType = ShipType.MILITARY_OPS;
        } else if (number == 36) {
            shipType = ShipType.SAILING;
        } else if (number == 37) {
            shipType = ShipType.PLEASURE_CRAFT;
        } else if (number >= 40 && number <= 49) {
            shipType = ShipType.HSC;
        } else if (number == 50) {
            shipType = ShipType.PILOT_VESSEL;
        } else if (number == 51) {
            shipType = ShipType.SEARCH_AND_RESCUE_VESSEL;
        } else if (number == 52) {
            shipType = ShipType.TUG;
        } else if (number == 53) {
            shipType = ShipType.PORT_TENDER;
        } else if (number == 54) {
            shipType = ShipType.ANTI_POLLUTION_EQUIPMENT;
        } else if (number == 58) {
            shipType = ShipType.MEDICAL_TRANSPORT;
        } else if (number >= 60 && number <= 69) {
            shipType = ShipType.PASSENGER;
        } else if (number >= 70 && number <= 79) {
            shipType = ShipType.CARGO;
        } else if (number >= 80 && number <= 89) {
            shipType = ShipType.TANKER;
        } else {
            shipType = ShipType.OTHER_TYPE;
        }
        return shipExtraRepository.findByShipType(shipType);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDb() {
        logger.info("Initialize DB by default values");
        shipExtraRepository.saveAll(List.of(
                ShipExtra.builder()
                        .shipType(ShipType.WING_IN_GROUND)
                        .imgUrl("https://www.ship-technology.com/wp-content/uploads/sites/16/2018/05/Image-1-Airfish-8-WIG-craft-e1525794302186.jpg")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.FISHING)
                        .imgUrl("https://images.unsplash.com/photo-1537136966191-df1c615c0fcf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZmlzaGluZyUyMGJvYXR8ZW58MHx8MHx8&w=1000&q=80")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.DREDGING_OR_UNDERWATER_OPS)
                        .imgUrl("https://www.marineinsight.com/wp-content/uploads/2019/07/dredgers-1.png")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.DIVING_OPS)
                        .imgUrl("https://i2.wp.com/workatship.com/wp-content/uploads/2021/07/diving-support-vessel.jpg?fit=650%2C432&ssl=1")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.MILITARY_OPS)
                        .imgUrl("https://static.vesselfinder.net/ship-photo/0-228762000-b717d1eaa38bcd0e2967aca9abb89dfe/1")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.SAILING)
                        .imgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSynUOFfiiPR9-vJzb7yoO3Gy7ctilgojZbEw&usqp=CAU")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.PLEASURE_CRAFT)
                        .imgUrl("http://community.fansshare.com/pic117/w/pleasure-craft/1200/14215_ships_boats_vehicles_boat_pleasure_craft_ship_desktop_hd_wallpaper.jpg")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.HSC)
                        .imgUrl("https://basilkaratzas.files.wordpress.com/2017/07/ms-champion-jet-7-bmk_4556.jpg")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.PILOT_VESSEL)
                        .imgUrl("https://static.vesselfinder.net/ship-photo/9568988-205591000-3c91ac7c40721d8c16fb4b0148bc53aa/1")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.SEARCH_AND_RESCUE_VESSEL)
                        .imgUrl("https://www.mauric.ecagroup.com/media-picture/101-750-500-sar-320-website-3.jpg")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.TUG)
                        .imgUrl("https://photos.marinetraffic.com/ais/showphoto.aspx?shipid=322005")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.PORT_TENDER)
                        .imgUrl("https://static.vesselfinder.net/ship-photo/0-244690030-350d046bbe5654fdeb6517d6818b3759/1")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.ANTI_POLLUTION_EQUIPMENT)
                        .imgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbLc9ww5XCXUUBU6npndl60zWlrY_YfaeC3SWIL3qIM43dMS7LrafEdPR09owwHgJ_pYA&usqp=CAU")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.MEDICAL_TRANSPORT)
                        .imgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8-gYvJY8-u5UEkoCIUIXozKbIzv9JKmSv6S3v7nLQiuWrOlXalWqa8ZhBTBy-7DTHtms&usqp=CAU")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.PASSENGER)
                        .imgUrl("https://www.kongsberg.com/globalassets/maritime/ship.jpg?quality=50")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.CARGO)
                        .imgUrl("https://c.files.bbci.co.uk/3225/production/_119773821_evergiven_cu2_jeffwelch_976.jpg")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.TANKER)
                        .imgUrl("https://photos.watchmedier.dk/Images/article11790600.ece/ALTERNATES/native-app-960/doc786reoavg3pzdkean4d.jpg")
                        .build(),
                ShipExtra.builder()
                        .shipType(ShipType.OTHER_TYPE)
                        .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1024px-No_image_3x4.svg.png")
                        .build()
        ));
    }

    public List<ShipExtra> getAllShipsExtra() {
        return shipExtraRepository.findAll();
    }

    public ShipExtra updateShipExtra(ShipType shipType, ShipExtra shipExtra) {
        logger.info("Update {} by new img value {}", shipType, shipExtra.getImgUrl());
        var shipExtraDb = shipExtraRepository.findByShipType(shipType);
        if (shipExtraDb == null) {
            shipExtraDb = ShipExtra.builder()
                    .shipType(shipType)
                    .build();
        }
        shipExtraDb.setImgUrl(shipExtra.getImgUrl());
        return shipExtraRepository.save(shipExtraDb);
    }
}
