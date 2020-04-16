package pl.org.grzybek.har.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class OrderBuyerCreateDto {

    @Email
    @NotEmpty
    private String email;
}
