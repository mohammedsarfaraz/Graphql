package com.example.applodemo

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.applodemo.databinding.FragmentLoginBinding
import com.example.rocketreserver.LoginMutation


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.GONE

        binding.loginBtn.setOnClickListener {

            var email = binding.emailEdt.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = getString(R.string.name_invalid)
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launchWhenResumed {
                val response = try {
                    apolloClient.mutation(LoginMutation(email = email)).execute()
                } catch (e: Exception) {
                    null
                }

                val token = response?.data?.login?.token
                Log.d("sarfraz","TokenStored $token")
                if (token == null || response.hasErrors()) {
                    binding.progressBar.visibility = View.GONE
//                       binding.submit.visibility = View.VISIBLE
                    return@launchWhenResumed


                }

                User.setToken(requireContext(),token)
                             findNavController().popBackStack()


            }

        }
    }
}