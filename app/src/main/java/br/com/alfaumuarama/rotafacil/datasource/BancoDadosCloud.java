package br.com.alfaumuarama.rotafacil.datasource;

import com.google.firebase.firestore.FirebaseFirestore;

public class BancoDadosCloud {
    // Mantenha uma instância estática para evitar múltiplas inicializações
    private static FirebaseFirestore firebaseFirestoreInstance;

    // Método estático para obter a instância do FirebaseFirestore
    public static FirebaseFirestore getFirestoreInstance() {
        if (firebaseFirestoreInstance == null) {
            // Se ainda não foi inicializado, inicialize-o
            firebaseFirestoreInstance = FirebaseFirestore.getInstance();
        }
        return firebaseFirestoreInstance;
    }
}
