package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController
{
    @Autowired
    PendudukService pendudukDAO;
    
    @Autowired
    KeluargaService keluargaDAO;
    
    @Autowired
	KelurahanService kelurahanDAO;
    
	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KotaService kotaDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/penduduk")
    public String viewPenduduk(Model model,
    		@RequestParam(value = "nik", required = false) String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        if(penduduk != null) {
        	KeluargaModel keluarga = keluargaDAO.selectKeluargaById(penduduk.getId_keluarga());
        	penduduk.setAlamat(keluarga.getAlamat());
        	penduduk.setRt(keluarga.getRt());
        	penduduk.setRw(keluarga.getRw());
        	
        	KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
        	penduduk.setNama_kelurahan(kelurahan.getNama_kelurahan());
        	
        	KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
        	penduduk.setNama_kecamatan(kecamatan.getNama_kecamatan());
        	
        	KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
        	penduduk.setNama_kota(kota.getNamaKota());
        	
        	model.addAttribute("penduduk", penduduk);
        	return "view-penduduk";
        } else {
        	model.addAttribute("nik", nik);
        	return "not-found-penduduk";
        }
    	
    }
    
    @RequestMapping("/keluarga")
	public String viewKeluarga(Model model,
			@RequestParam(value = "nkk", required = false) String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

		if (keluarga != null) {
			log.info("sukses");
			KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
			keluarga.setNama_kelurahan(kelurahan.getNama_kelurahan());
			
			KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
			keluarga.setNama_kecamatan(kecamatan.getNama_kecamatan());
			
			KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
			keluarga.setNama_kota(kota.getNamaKota());
			
			model.addAttribute("keluarga", keluarga);

			return "view-keluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "not-found-keluarga";
		}
	}
    
    @RequestMapping("/penduduk/tambah")
    public String addPenduduk(Model model) {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk", penduduk);
		return "form-add-penduduk";
	}
    
    @RequestMapping(value = "/penduduk/tambah/submit", method = RequestMethod.POST)
	public String addPendudukSubmit(Model model, @ModelAttribute PendudukModel penduduk) {
    	String[] arr = penduduk.getTanggal_lahir().split("-");
		String tahun = arr[0];
		String tahunLahir = tahun.substring(2);
		String bulan = arr[1];
		String tanggal = arr[2];
		
		KeluargaModel keluarga = keluargaDAO.selectKeluargaById(penduduk.getId_keluarga());
		
		if(penduduk.getJenis_kelamin() == 1) {
			int temp = Integer.parseInt(tanggal);
			temp += 40;
			tanggal = "" + temp;
		}
		
		Long nik = Long.parseLong(keluarga.getNkk().substring(0,6) + tanggal + bulan + tahunLahir + "0001");
    	
		while(true) {
			PendudukModel check = pendudukDAO.selectPenduduk(""+nik);
			if (check != null) {
				nik++;
			} else {
				break;
			}
		}
		penduduk.setNik(""+nik);
		
		pendudukDAO.addPenduduk(penduduk);
		model.addAttribute("nik", nik);
		
		return "success-add";
	}
    
    @RequestMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		model.addAttribute("penduduk", penduduk);
		return "form-update-penduduk";
	}
    
    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String updatePenduduk(Model model, @PathVariable(value = "nik") String nik,
			@ModelAttribute PendudukModel penduduk) {
		PendudukModel pendudukLama = pendudukDAO.selectPenduduk(nik);

		penduduk.setId(pendudukLama.getId());
		pendudukDAO.updatePenduduk(penduduk);
		penduduk.setNik(nik);

		if (!pendudukLama.getTanggal_lahir().equals(penduduk.getTanggal_lahir())) {
			pendudukLama.generateNik(keluargaDAO, pendudukDAO);
		}

		model.addAttribute("nik", pendudukLama.getNik());
		return "sukses-update";
	}
    
    @RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String pendudukMati(Model model, @RequestParam(value = "nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		pendudukDAO.setWafatPenduduk(penduduk);
		
		model.addAttribute("nik", nik);
		return "sukses-set-wafat";
	}
    
    @RequestMapping("/keluarga/ubah/{nkk}")
	public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		model.addAttribute("allKelurahan", kelurahanDAO.selectAllKelurahan());
		model.addAttribute("keluarga", keluarga);
		return "form-update-keluarga";
	}
    
    @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk,
			@ModelAttribute KeluargaModel keluarga) {
		KeluargaModel keluargaLama = keluargaDAO.selectKeluarga(nkk);
		keluarga.setNkk(keluargaLama.getNkk());
		keluarga.setId(keluargaLama.getId());
		keluarga.setIs_tidak_berlaku(keluargaLama.getIs_tidak_berlaku());

		if (keluargaLama.getId_kelurahan() != keluarga.getId_kelurahan()) {
			keluarga.generateNkk(keluargaDAO);
		}
		keluargaDAO.updateKeluarga(keluarga);

		model.addAttribute("nkk", keluargaLama.getNkk());
		return "sukses-update";
	}
    
    @RequestMapping(value = "/penduduk/cari")
	public String cariPendudukK(Model model, @RequestParam(value = "id_kota", required = false) int kt,
			@RequestParam(value = "id_kecamatan", required = false) String kc,
			@RequestParam(value = "id_kelurahan", required = false) String kl) {

		List<KotaModel> list_kota = kotaDAO.selectAllKota();
		model.addAttribute("list_kota", list_kota);
		model.addAttribute("id_kota", kt);

		List<KecamatanModel> list_kecamatan = kecamatanDAO.selectKecamatanByIdKota(kt);
		model.addAttribute("list_kecamatan", list_kecamatan);
		model.addAttribute("id_kecamatan", kc);

		List<KelurahanModel> list_kelurahan = kelurahanDAO.selectKelurahanByIdKecamatan(kc);
		model.addAttribute("list_kelurahan", list_kelurahan);
		model.addAttribute("id_kelurahan", kl);
		
		if (kt != 0 && kc != null && kl != null) {
			List<PendudukModel> list_penduduk = pendudukDAO.selectPendudukByIdKelurahan(kl);
		
			KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(Integer.parseInt(kl));
			KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
			KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
			
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			model.addAttribute("penduduk", list_penduduk);
			
			return "list-penduduk";
		}
		
		return "cari-penduduk";
	}
    

}
