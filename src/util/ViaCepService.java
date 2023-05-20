package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

public class ViaCepService {
    static String webService = "http://viacep.com.br/ws/";
    static int codigoSucesso = 200;

    public static ViaCepResponse buscaEnderecoPelo(String cep) throws Exception {
        String urlParaChamada = webService + cep + "/json";

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code: " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String jsonEmString = Util.converteJsonEmString(resposta);

            Gson gson = new Gson();
            ViaCepResponse cepResponse = gson.fromJson(jsonEmString, ViaCepResponse.class);

            return cepResponse;
        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
    }

}
